package org.example.mypet.Service;

import lombok.RequiredArgsConstructor;

import org.example.mypet.DTO.Invoice.InvoiceDTO;
import org.example.mypet.Models.Customer;
import org.example.mypet.Models.Invoice;
import org.example.mypet.Models.InvoiceDetail;
import org.example.mypet.Models.Pet;
import org.example.mypet.Repository.CustomerRepository;
import org.example.mypet.Repository.InvoiceRepository;
import org.example.mypet.Repository.PetRepository;
import org.example.mypet.Utils.Mapper.InvoiceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    @Transactional
    public InvoiceDTO createInvoice(InvoiceDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Invoice invoice = InvoiceMapper.toEntity(dto);
        invoice.setCustomer(customer);

        List<InvoiceDetail> details = invoice.getDetails().stream().map(d -> {
            Pet pet = petRepository.findById(d.getPet().getId())
                    .orElseThrow(() -> new RuntimeException("Pet not found: " + d.getPet().getId()));
            if (pet.getQuantity() < d.getQuantity()) {
                throw new RuntimeException("Not enough stock for pet: " + pet.getName());
            }

            pet.setQuantity(pet.getQuantity() - d.getQuantity());
            petRepository.save(pet);

            d.setPet(pet);
            d.setSubTotal(d.getQuantity() * d.getUnitPrice());
            d.setInvoice(invoice);
            return d;
        }).toList();

        invoice.setDetails(details);

        double total = details.stream().mapToDouble(InvoiceDetail::getSubTotal).sum();
        invoice.setTotalAmount(total);

        Invoice saved = invoiceRepository.save(invoice);

        return InvoiceMapper.toDTO(saved);
    }

    public List<InvoiceDTO> getAll() {
        return invoiceRepository.findAll().stream().map(InvoiceMapper::toDTO).toList();
    }

    public InvoiceDTO getById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return InvoiceMapper.toDTO(invoice);
    }
}
