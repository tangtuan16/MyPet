package org.example.mypet.DTO.Pet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDTO {
    private Long id;
    private String name;
    private Long speciesId;
    private String speciesName;
    private String breed;
    private Integer age;
    private String gender;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private Long supplierId;
    private String supplierName;
}
