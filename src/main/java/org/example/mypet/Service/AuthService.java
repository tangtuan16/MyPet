package org.example.mypet.Service;

import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.Auth.UserRegister;
import org.example.mypet.Models.Role;
import org.example.mypet.Models.User;
import org.example.mypet.Repository.RoleRepository;
import org.example.mypet.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(UserRegister dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username is already in use");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        Role defaultRole = roleRepository.findByName("ROLE_EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("No role found"));

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .name(dto.getName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .roles(Set.of(defaultRole))
                .build();
        userRepository.save(user);
        return true;
    }

    public void resetPasswordAsAdmin(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
