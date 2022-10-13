package com.example.proj.controller;

import com.example.proj.entity.User;
import com.example.proj.jwt.JwtTokenProvider;
import com.example.proj.request.AuthRequest;
import com.example.proj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody AuthRequest authRequest){
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            User user = userService.getUser(email);
            if(user == null){
                throw new UsernameNotFoundException("Username not found");
            }
        }
        catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email or password");
        }
        UserDetails userDetails = userService.loadUserByUsername(email);
        final String token = new JwtTokenProvider().generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
