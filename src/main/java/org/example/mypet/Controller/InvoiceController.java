package org.example.mypet.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.Invoice.InvoiceDTO;
import org.example.mypet.Service.InvoiceService;
import org.example.mypet.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<ApiResponse<InvoiceDTO>> createInvoice(@Valid @RequestBody InvoiceDTO dto) {
        InvoiceDTO created = invoiceService.createInvoice(dto);
        return ApiResponse.success(created, HttpStatus.CREATED.value(), "Invoice created successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceDTO>>> getAll() {
        return ApiResponse.success(invoiceService.getAll(), HttpStatus.OK.value(), "All invoices");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceDTO>> getById(@PathVariable Long id) {
        return ApiResponse.success(invoiceService.getById(id), HttpStatus.OK.value(), "Invoice details");
    }
}
