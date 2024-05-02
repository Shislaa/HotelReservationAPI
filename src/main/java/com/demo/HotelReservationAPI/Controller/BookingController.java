package com.demo.HotelReservationAPI.Controller;

import com.demo.HotelReservationAPI.DTO.BookingRequestDto;
import com.demo.HotelReservationAPI.DTO.BookingResponseDto;
import com.demo.HotelReservationAPI.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/addBooking")
    public void addBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        bookingService.addBooking(bookingRequestDto);
    }

    @GetMapping("/getAllBookings")
    public List<BookingResponseDto> getAllBooking() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/getBookingByCustomerId")
    public List<BookingResponseDto> getBookingByCustomerId(@RequestParam Long customerId) {
        return bookingService.getBookingsByCustomerId(customerId);
    }
}
