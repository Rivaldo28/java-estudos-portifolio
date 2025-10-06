package com.example.chatbotapi.service;

import com.example.chatbotapi.dto.CustomerDTO;
import com.example.chatbotapi.exception.ResourceNotFoundException;
import com.example.chatbotapi.model.Customer;
import com.example.chatbotapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    
    public Customer findOrCreateCustomer(CustomerDTO customerDTO) {
        if (customerDTO.getEmail() == null || customerDTO.getEmail().isEmpty()) {
            return null;
        }
        
        return customerRepository.findByEmail(customerDTO.getEmail())
                .orElseGet(() -> {
                    Customer newCustomer = new Customer();
                    newCustomer.setEmail(customerDTO.getEmail());
                    newCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
                    return customerRepository.save(newCustomer);
                });
    }
    
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }
    
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));
    }
    
    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = getCustomerById(id);
        
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        
        return customerRepository.save(customer);
    }
    
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}