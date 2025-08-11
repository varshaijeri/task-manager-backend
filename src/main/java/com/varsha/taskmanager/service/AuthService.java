package com.varsha.taskmanager.service;

import com.varsha.taskmanager.entity.User;
import com.varsha.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public String register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameNotFoundException("Username " + username + " already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(String username, String password) {
        User user =  userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Username " + username + " not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        return jwtService.generateToken(user.getUsername());
    }
}
