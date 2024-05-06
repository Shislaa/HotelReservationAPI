package com.demo.HotelReservationAPI.Controller;

import com.demo.HotelReservationAPI.DTO.CustomerRequestDto;
import com.demo.HotelReservationAPI.DTO.CustomerResponseDto;
import com.demo.HotelReservationAPI.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
        customerRequestDto.setFirstName(firstName);
        customerRequestDto.setLastName(lastName);
        customerRequestDto.setEmail(email);
        customerRequestDto.setPhone(phone);
        customerRequestDto.setAddress(address);
        customerRequestDto.setCity(city);
        customerRequestDto.setState(state);
        customerRequestDto.setZip(zip);
        customerRequestDto.setCountry(country);
        service.createCustomer(customerRequestDto);
    }

    @GetMapping("/getAllCustomer")
    public List<CustomerResponseDto> getAllCustomers(){
        return service.getAllCustomers();
    }

    @GetMapping("/getCustomerById")
    public ResponseEntity<Object> getCustomerById(@RequestParam(value = "id") Long id) {
        return service.getCustomerById(id);
    }
}
