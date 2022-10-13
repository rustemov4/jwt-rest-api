package com.example.proj.service;

import com.example.proj.entity.Product;
import com.example.proj.entity.User;
import com.example.proj.repository.ProductRepository;
import com.example.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public User saveUser(User user) {
        boolean existsUser = userRepository.findByEmail(user.getEmail()).isPresent();
        if (existsUser) {
            return null;
        }
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUser(String email){
        return userRepository.findUserByEmail(email);
    }
    public User saveUserProduct(Long id, Long productId) {
        User user = userRepository.findById(id).get();
        Product product = productRepository.findById(productId).get();
        List<Product> products = user.getProducts();
        products.add(product);
        user.setProducts(products);
        return userRepository.save(user);
    }

    public List<Product> getProducts(Long id) {
        boolean existsUser = userRepository.findById(id).isPresent();
        if (!existsUser) {
            return null;
        }
        User currentUser = userRepository.findById(id).get();
        return currentUser.getProducts();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
