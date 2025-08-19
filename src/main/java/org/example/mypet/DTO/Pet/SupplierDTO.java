package org.example.mypet.DTO.Pet;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {

    private Long id;

    @NotBlank(message = "Supplier name cannot be blank")
    @Size(min = 2, max = 100, message = "Supplier name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(
            regexp = "^(\\+84|0)[0-9]{9,10}$",
            message = "Phone must be a valid Vietnamese phone number"
    )
    private String phone;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;
}