package com.proyectos2rescuelink.proyectos2_rescuelink.service;

import com.proyectos2rescuelink.proyectos2_rescuelink.model.Role;
import com.proyectos2rescuelink.proyectos2_rescuelink.model.User;
import com.proyectos2rescuelink.proyectos2_rescuelink.repository.RoleRepository;
import com.proyectos2rescuelink.proyectos2_rescuelink.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("El usuario ya existe.");
        }

        Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("USER");
            return roleRepository.save(newRole);
        });

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // ⚠️ Sin encriptar (para producción, usar BCrypt)
        newUser.setRoles(Set.of(userRole));

        return userRepository.save(newUser);
    }

    public Optional<User> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password)); // ⚠️ Comparación directa (para producción, usar BCrypt)
    }
}
