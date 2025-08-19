package org.example.mypet.DTO.Invoice;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InvoiceDTO {
    private Long id;

    @NotNull(message = "Customer is required")
    private Long customerId;

    private Date invoiceDate;

    @NotNull(message = "Invoice details are required")
    @Size(min = 1, message = "Invoice must contain at least one item")
    private List<InvoiceDetailDTO> details;

    private Double totalAmount;
}
