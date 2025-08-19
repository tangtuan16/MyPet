package org.example.mypet.Service;

import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.Pet.SupplierDTO;
import org.example.mypet.Models.Supplier;
import org.example.mypet.Repository.SupplierRepository;
import org.example.mypet.Utils.Mapper.SupplierMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierDTO createSupplier(SupplierDTO dto) {
        if (supplierRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Phone already exists.");
        }
        if (supplierRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        Supplier supplier = Supplier.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .active(true)
                .build();

        return SupplierMapper.toDTO(supplierRepository.save(supplier));
    }

    public SupplierDTO updateSupplier(Long id, SupplierDTO dto) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        if (supplier.getPhone().equals(dto.getPhone())
                && supplierRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Phone already exists.");
        }

        if (supplier.getEmail().equals(dto.getEmail())
                && supplierRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        supplier.setName(dto.getName());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setAddress(dto.getAddress());

        return SupplierMapper.toDTO(supplierRepository.save(supplier));
    }

    public SupplierDTO deactivateSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setActive(false);
        return SupplierMapper.toDTO(supplierRepository.save(supplier));
    }

    public List<SupplierDTO> getSuppliers() {
        return supplierRepository.findAllByActiveTrue()
                .stream()
                .map(SupplierMapper::toDTO)
                .toList();
    }
}
