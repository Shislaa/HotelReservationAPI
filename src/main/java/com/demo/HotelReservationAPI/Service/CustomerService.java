package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.DTO.BookingResponseDto;
import com.demo.HotelReservationAPI.DTO.CustomerRequestDto;
import com.demo.HotelReservationAPI.DTO.CustomerResponseDto;
import com.demo.HotelReservationAPI.Entity.BookingDetails;
import com.demo.HotelReservationAPI.Entity.CustomerDetails;
import com.demo.HotelReservationAPI.Repository.CustomerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerDetailsRepository customerDetailsRepository;

    private BookingService bookingService;

    @Autowired
    public CustomerService(CustomerDetailsRepository customerDetailsRepository,
                           BookingService bookingService) {
        this.customerDetailsRepository = customerDetailsRepository;
        this.bookingService = bookingService;
    }

    public CustomerResponseDto toDTO (CustomerDetails customerDetails) {
        CustomerResponseDto customerResDto = new CustomerResponseDto();
        customerResDto.setFirstName(customerDetails.getFirstName());
        customerResDto.setLastName(customerDetails.getLastName());
        customerResDto.setEmail(customerDetails.getEmail());
        customerResDto.setId(customerDetails.getCustomerID());
        customerResDto.setPhone(customerDetails.getPhone());
        List<BookingDetails> bookingDetailsList = customerDetails.getBookingList();
        List<BookingResponseDto> bookingResponseDtoList = new ArrayList<>();
        bookingDetailsList.forEach(booking -> {
            bookingResponseDtoList.add(bookingService.toBookingDto(booking));
        });
        customerResDto.setBookings(bookingResponseDtoList);
        return customerResDto;
    }

    private CustomerDetails toEntity (CustomerRequestDto customerRequestDto) {
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setFirstName(customerRequestDto.getFirstName());
        customerDetails.setLastName(customerRequestDto.getLastName());
        customerDetails.setEmail(customerRequestDto.getEmail());
        customerDetails.setPhone(customerRequestDto.getPhone());
        customerDetails.setAddress(customerRequestDto.getAddress());
        customerDetails.setCity(customerRequestDto.getCity());
        customerDetails.setState(customerRequestDto.getState());
        customerDetails.setCountry(customerRequestDto.getCountry());
        customerDetails.setZip(customerRequestDto.getZip());
        return customerDetails;

    }

    public void createCustomer (CustomerRequestDto customerRequestDto) {
        CustomerDetails customerDetails = toEntity(customerRequestDto);
        customerDetailsRepository.save(customerDetails);
    }

    public List<CustomerResponseDto> getAllCustomers() {
        List<CustomerDetails> customers = customerDetailsRepository.findAll();
        List<CustomerResponseDto> customerResponseDtos = new ArrayList<>();
        customers.forEach(customer -> customerResponseDtos.add(toDTO(customer)));
        return customerResponseDtos;
    }

    public ResponseEntity<Object> getCustomerById(Long id) {
        CustomerDetails customer = customerDetailsRepository.findByCustomerID(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(toDTO(customer));
        }
    }
}
