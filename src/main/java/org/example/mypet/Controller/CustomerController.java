package org.example.mypet.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.User.CustomerDTO;
import org.example.mypet.Service.CustomerService;
import org.example.mypet.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.example.mypet.Service.CustomerService;
import org.example.mypet.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDTO>> create(@Valid @RequestBody CustomerDTO dto) {
        CustomerDTO result = customerService.create(dto);
        return ApiResponse.success(result, HttpStatus.CREATED.value(), "Customer created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> getById(@PathVariable Long id) {
        CustomerDTO result = customerService.getById(id);
        return ApiResponse.success(result, HttpStatus.OK.value(), "Customer retrieved successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAll() {
        List<CustomerDTO> result = customerService.getAll();
        return ApiResponse.success(result, HttpStatus.OK.value(), "All customers retrieved successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> update(@PathVariable Long id,@Valid @RequestBody CustomerDTO dto) {
        CustomerDTO result = customerService.update(id, dto);
        return ApiResponse.success(result, HttpStatus.OK.value(), "Customer updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ApiResponse.success(null, HttpStatus.NO_CONTENT.value(), "Customer deleted successfully");
    }
}
