package com.proyectos2rescuelink.proyectos2_rescuelink.controller;

import com.proyectos2rescuelink.proyectos2_rescuelink.model.User;
import com.proyectos2rescuelink.proyectos2_rescuelink.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        try {
            User newUser = userService.registerUser(username, password);
            return ResponseEntity.ok(Map.of("message", "Usuario registrado con éxito", "user", newUser.getUsername()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        Optional<User> user = userService.authenticate(username, password);
        if (user.isPresent()) {
            return ResponseEntity.ok(Map.of("message", "Login exitoso", "user", user.get().getUsername()));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
        }
    }
}
