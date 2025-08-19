package org.example.mypet.Service;

import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.User.CustomerDTO;
import org.example.mypet.Models.Customer;
import org.example.mypet.Models.User;
import org.example.mypet.Repository.CustomerRepository;
import org.example.mypet.Utils.Mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import org.example.mypet.Models.Customer;
import org.example.mypet.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDTO create(CustomerDTO dto) {
        if (customerRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Phone number already in use");
        }
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        Customer customer = CustomerMapper.dtoToEntity(dto);
        return CustomerMapper.entityToDto(customerRepository.save(customer));
    }

    public CustomerDTO getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return CustomerMapper.entityToDto(customer);
    }

    public List<CustomerDTO> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public CustomerDTO update(Long id, CustomerDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!customer.getPhone().equals(dto.getPhone()) && customerRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Phone number already in use");
        }
        if (!customer.getEmail().equals(dto.getEmail()) && customerRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        customer.setName(dto.getName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());

        return CustomerMapper.entityToDto(customerRepository.save(customer));
    }

    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }
}
