package org.example.mypet.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.Pet.SupplierDTO;
import org.example.mypet.Service.SupplierService;
import org.example.mypet.Utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<SupplierDTO>> create(@Valid @RequestBody SupplierDTO dto) {
        return ApiResponse.success(supplierService.createSupplier(dto), "Created successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierDTO>> update(@PathVariable Long id, @Valid @RequestBody SupplierDTO dto) {
        return ApiResponse.success(supplierService.updateSupplier(id, dto), "Updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        supplierService.deactivateSupplier(id);
        return ApiResponse.success(null, "Deactivated successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierDTO>>> getAll() {
        return ApiResponse.success(
                supplierService.getSuppliers(), 200, "Fetched successfully");
    }
}
