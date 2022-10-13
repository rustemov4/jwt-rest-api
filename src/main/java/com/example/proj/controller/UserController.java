package com.example.proj.controller;

import com.example.proj.entity.Role;
import com.example.proj.entity.User;
import com.example.proj.repository.RoleRepository;
import com.example.proj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @PostMapping("/user")
    private ResponseEntity<?> save(@RequestBody User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        List<Role> usrRoles = new ArrayList<>();
        Role role = roleRepository.findRoleByName("ROLE_USER");
        usrRoles.add(role);
        newUser.setRoles(usrRoles);
        if (userService.saveUser(newUser) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this login exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @GetMapping("/user")
    private ResponseEntity<?>getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(email));
    }

    @GetMapping("/users")
    private ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PostMapping("/user/{id}/product/{productId}")
    private ResponseEntity<?> setUserProducts(@PathVariable Long id, @PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUserProduct(id, productId));
    }

    @GetMapping("/user/{id}/products")
    private ResponseEntity<?> getUserProducts(@PathVariable Long id) {
        if (userService.getProducts(id) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have products");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.getProducts(id));
    }


}
