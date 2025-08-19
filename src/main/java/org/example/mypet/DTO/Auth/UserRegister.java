package org.example.mypet.DTO.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegister {
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9._-]+$",
            message = "Username must not contain spaces and can only include letters, numbers, '.', '_' and '-'"
    )
    private String username;

    @NotBlank(message = "Password is required")
    @Size(max = 50, message = "Password must not exceed 50 characters")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "Password must contain at least one letter and one number"
    )
    private String password;

    @NotBlank(message = "Email is required")
    @Size(max = 50, message = "Email must not exceed 50 characters")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Name cannot be null")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Phone cannot be null")
    @Pattern(
            regexp = "^(0[0-9]{9}|\\+84[0-9]{9})$",
            message = "Phone must start with 0 or +84 and contain 10 digits"
    )
    private String phone;

    @NotBlank(message = "Address cannot be null")
    private String address;

}
