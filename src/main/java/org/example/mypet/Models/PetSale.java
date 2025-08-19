package org.example.mypet.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pet_sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pet pet;

    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;

    @ManyToOne
    private Invoice invoice;
}
