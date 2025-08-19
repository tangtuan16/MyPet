package org.example.mypet.Utils.Mapper;

import org.example.mypet.DTO.User.CustomerDTO;
import org.example.mypet.Models.Customer;
import org.example.mypet.Models.User;

public class CustomerMapper {

    public static Customer dtoToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        return customer;
    }

    public static CustomerDTO entityToDto(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        return dto;
    }
}
