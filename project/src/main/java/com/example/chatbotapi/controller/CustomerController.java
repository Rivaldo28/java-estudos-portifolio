package com.example.chatbotapi.controller;

import com.example.chatbotapi.dto.CustomerDTO;
import com.example.chatbotapi.model.Customer;
import com.example.chatbotapi.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    
    private final CustomerService customerService;
    
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        CustomerDTO customerDTO = convertToDTO(customer);
        return ResponseEntity.ok(customerDTO);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        CustomerDTO customerDTO = convertToDTO(customer);
        return ResponseEntity.ok(customerDTO);
    }
    
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.findOrCreateCustomer(customerDTO);
        CustomerDTO createdCustomerDTO = convertToDTO(customer);
        return new ResponseEntity<>(createdCustomerDTO, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerDTO customerDTO) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDTO);
        CustomerDTO updatedCustomerDTO = convertToDTO(updatedCustomer);
        return ResponseEntity.ok(updatedCustomerDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        return dto;
    }
}