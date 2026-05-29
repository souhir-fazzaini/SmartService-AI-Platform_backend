package org.example.SmartService;

import org.example.SmartService.dto.AuthRequest;
import org.example.SmartService.dto.AuthResponse;
import org.example.SmartService.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.SmartService.repositories.UserRepository;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final org.example.SmartService.JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired  // ← Spring va injecter les dépendances
    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }



    public AuthResponse register(AuthRequest request) {

        // check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        // 🔐 CRYPTER le mot de passe avant de le sauvegarder
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔐 VÉRIFIER le mot de passe crypté
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}
