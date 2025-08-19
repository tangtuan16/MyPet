package org.example.mypet.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoice_details")
@Data
@NoArgsConstructor
public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private Integer quantity;

    private Double unitPrice;

    private Double subTotal;
}
