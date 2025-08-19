package org.example.mypet.DTO.User;

import lombok.*;
import org.example.mypet.Models.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Set<Role> roles;
}
