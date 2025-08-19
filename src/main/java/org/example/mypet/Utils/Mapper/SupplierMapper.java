package org.example.mypet.Utils.Mapper;

import org.example.mypet.DTO.Pet.SupplierDTO;
import org.example.mypet.Models.Supplier;

public class SupplierMapper {
    public static SupplierDTO toDTO(Supplier supplier) {
        return SupplierDTO.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .phone(supplier.getPhone())
                .email(supplier.getEmail())
                .address(supplier.getAddress())
                .build();
    }
}
