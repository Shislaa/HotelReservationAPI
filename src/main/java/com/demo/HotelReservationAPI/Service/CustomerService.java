package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.DTO.CustomerDTO;
import com.demo.HotelReservationAPI.Entity.CustomerDetails;
import com.demo.HotelReservationAPI.Repository.CustomerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerDetailsRepository repository;

    @Autowired
    public CustomerService(CustomerDetailsRepository repository) {
        this.repository = repository;
    }

    public CustomerDTO toDTO (CustomerDetails customerDetails) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(customerDetails.getFirstName());
        customerDTO.setLastName(customerDetails.getLastName());
        customerDTO.setEmail(customerDetails.getEmail());
        customerDTO.setId(customerDetails.getCustomerID());
        customerDTO.setPhone(customerDetails.getPhone());
        customerDTO.setAddress(customerDetails.getAddress());
        customerDTO.setCity(customerDetails.getCity());
        customerDTO.setState(customerDetails.getState());
        customerDTO.setCountry(customerDetails.getCountry());
        customerDTO.setZip(customerDetails.getZip());
        customerDTO.setBookingList(customerDetails.getBookingList());
        return customerDTO;
    }

    private CustomerDetails toEntity (CustomerDTO customerDTO) {
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setFirstName(customerDTO.getFirstName());
        customerDetails.setLastName(customerDTO.getLastName());
        customerDetails.setEmail(customerDTO.getEmail());
        customerDetails.setPhone(customerDTO.getPhone());
        customerDetails.setAddress(customerDTO.getAddress());
        customerDetails.setCity(customerDTO.getCity());
        customerDetails.setState(customerDTO.getState());
        customerDetails.setCountry(customerDTO.getCountry());
        customerDetails.setZip(customerDTO.getZip());
        customerDetails.setBookingList(customerDTO.getBookingList());
        return customerDetails;

    }

    public void createCustomer (CustomerDTO customerDTO) {
        CustomerDetails customerDetails = toEntity(customerDTO);
        repository.save(customerDetails);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDetails> customers = repository.findAll();
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        customers.forEach(customer -> customerDTOs.add(toDTO(customer)));
        return customerDTOs;
    }
}
