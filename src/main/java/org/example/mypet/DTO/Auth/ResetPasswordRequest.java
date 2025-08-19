package org.example.mypet.DTO.Auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "New password is required")
    private String newPassword;
}
