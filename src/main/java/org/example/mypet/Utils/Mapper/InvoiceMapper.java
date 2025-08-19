package org.example.mypet.Utils.Mapper;

import org.example.mypet.DTO.Invoice.InvoiceDTO;
import org.example.mypet.DTO.Invoice.InvoiceDetailDTO;
import org.example.mypet.Models.Invoice;
import org.example.mypet.Models.InvoiceDetail;

import java.util.List;
import java.util.stream.Collectors;

public class InvoiceMapper {

    public static InvoiceDTO toDTO(Invoice invoice) {
        if (invoice == null) return null;

        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(invoice.getId());
        dto.setCustomerId(invoice.getCustomer() != null ? invoice.getCustomer().getId() : null);
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setTotalAmount(invoice.getTotalAmount());

        List<InvoiceDetailDTO> details = invoice.getDetails() != null
                ? invoice.getDetails().stream().map(InvoiceMapper::detailToDTO).collect(Collectors.toList())
                : List.of();

        dto.setDetails(details);
        return dto;
    }

    public static Invoice toEntity(InvoiceDTO dto) {
        if (dto == null) return null;

        Invoice invoice = new Invoice();
        invoice.setId(dto.getId());
        invoice.setInvoiceDate(dto.getInvoiceDate());
        invoice.setTotalAmount(dto.getTotalAmount());

        List<InvoiceDetail> details = dto.getDetails() != null
                ? dto.getDetails().stream().map(detailDTO -> detailToEntity(detailDTO, invoice)).collect(Collectors.toList())
                : List.of();

        invoice.setDetails(details);
        return invoice;
    }

    public static InvoiceDetailDTO detailToDTO(InvoiceDetail detail) {
        if (detail == null) return null;

        InvoiceDetailDTO dto = new InvoiceDetailDTO();
        dto.setPetId(detail.getPet() != null ? detail.getPet().getId() : null);
        dto.setQuantity(detail.getQuantity());
        dto.setUnitPrice(detail.getUnitPrice());
        return dto;
    }

    public static InvoiceDetail detailToEntity(InvoiceDetailDTO dto, Invoice invoice) {
        if (dto == null) return null;

        InvoiceDetail detail = new InvoiceDetail();
        detail.setInvoice(invoice);
        detail.setQuantity(dto.getQuantity());
        detail.setUnitPrice(dto.getUnitPrice());
        return detail;
    }
}
