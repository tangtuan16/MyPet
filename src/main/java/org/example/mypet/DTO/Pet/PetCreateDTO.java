package org.example.mypet.DTO.Pet;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetCreateDTO {

    @NotBlank(message = "Pet name cannot be blank")
    private String name;

    @NotNull(message = "Species is required")
    private Long speciesId;

    private String breed;

    @Min(value = 0, message = "Age cannot be negative")
    private Integer age;

    @Pattern(regexp = "Male|Female", message = "Gender must be Male or Female")
    private String gender;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Supplier is required")
    private Long supplierId;
}
