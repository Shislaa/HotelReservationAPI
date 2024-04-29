package com.demo.HotelReservationAPI.Controller;

import com.demo.HotelReservationAPI.DTO.CustomerDTO;
import com.demo.HotelReservationAPI.Entity.CustomerDetails;
import com.demo.HotelReservationAPI.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v2/api/customer")
public class CustomerController {

    private CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void createCustomer(@RequestParam(value = "firstName") String firstName,
                               @RequestParam(value = "lastName") String lastName,
                               @RequestParam(value = "email") String email,
                               @RequestParam(value = "phone") String phone,
                               @RequestParam(value = "address") String address,
                               @RequestParam(value = "city") String city,
                               @RequestParam(value = "state") String state,
                               @RequestParam(value = "zip") String zip,
                               @RequestParam(value = "country") String country){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(firstName);
        customerDTO.setLastName(lastName);
        customerDTO.setEmail(email);
        customerDTO.setPhone(phone);
        customerDTO.setAddress(address);
        customerDTO.setCity(city);
        customerDTO.setState(state);
        customerDTO.setZip(zip);
        customerDTO.setCountry(country);
        service.createCustomer(customerDTO);
    }

    @GetMapping("/getAllCustomer")
    public List<CustomerDTO> getAllCustomers(){
        return service.getAllCustomers();
    }

    @GetMapping("/getCustomerById")
    public CustomerDTO getCustomerById(@RequestParam(value = "id") Long id){
        return service.getCustomerById(id);
    }
}
