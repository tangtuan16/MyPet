package org.example.mypet.Configs;

import lombok.RequiredArgsConstructor;
import org.example.mypet.Models.Role;
import org.example.mypet.Models.User;
import org.example.mypet.Repository.RoleRepository;
import org.example.mypet.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Bean
    CommandLineRunner init() {
        return args -> {
            var rAdmin = roleRepo.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepo.save(Role.builder().name("ROLE_ADMIN").build()));
            var rUser = roleRepo.findByName("ROLE_EMPLOYEE")
                    .orElseGet(() -> roleRepo.save(Role.builder().name("ROLE_EMPLOYEE").build()));
            if (!userRepo.existsByUsername("admin")) {
                var u = User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .name("admin")
                        .phone("0123456789")
                        .address("ETC")
                        .password(encoder.encode("admin123"))
                        .roles(Set.of(rUser, rAdmin))
                        .build();
                userRepo.save(u);
            }
        };
    }
}
