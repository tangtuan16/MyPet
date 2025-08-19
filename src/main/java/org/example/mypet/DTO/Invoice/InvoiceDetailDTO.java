package org.example.mypet.DTO.Invoice;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class InvoiceDetailDTO {
    @NotNull(message = "Pet ID is required")
    private Long petId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @Positive(message = "Unit price must be positive")
    private Double unitPrice;
}
