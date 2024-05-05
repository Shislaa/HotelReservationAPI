package com.demo.HotelReservationAPI.Controller;

import com.demo.HotelReservationAPI.DTO.BookingRequestDto;
import com.demo.HotelReservationAPI.DTO.BookingResponseDto;
import com.demo.HotelReservationAPI.DTO.RoomResponseDto;
import com.demo.HotelReservationAPI.Entity.RoomDetails;
import com.demo.HotelReservationAPI.Service.BookingService;
import com.demo.HotelReservationAPI.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;

    private RoomService roomService;

    @Autowired
    public BookingController(BookingService bookingService,
                             RoomService roomService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
    }

    @PostMapping("/addBooking")
    public ResponseEntity<String> addBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        Boolean isBookingSaved =  bookingService.addBooking(bookingRequestDto);
        if (isBookingSaved) {
            return ResponseEntity.ok("Booking added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Booking already exists");
        }
    }

    @GetMapping("/getAllBookings")
    public List<BookingResponseDto> getAllBooking() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/cancelBooking")
    public BookingResponseDto cancelBooking(@RequestParam Long bookingId) {
        return bookingService.cancelBooking(bookingId);
    }

    @GetMapping("/getBookingByCustomerId")
    public List<BookingResponseDto> getBookingByCustomerId(@RequestParam Long customerId) {
        return bookingService.getBookingsByCustomerId(customerId);
    }

    @GetMapping("/getBookingById")
    public BookingResponseDto getBookingById(@RequestParam Long bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @PostMapping("/updateBooking")
    public BookingResponseDto updateBookingDetails(@RequestParam Long bookingId,
                                                   @RequestParam(required = false) Date newStartDate,
                                                   @RequestParam(required = false) Date newEndDate,
                                                   @RequestParam(required = false) Long newRoomId ) {
        return bookingService.updateBooking(bookingId, newStartDate, newEndDate, newRoomId);
    }

    @GetMapping("/searchForBooking")
    public List<RoomResponseDto> searchForAvailableRooms(@RequestParam Long hotelId,
                                                         @RequestParam Date startDate,
                                                         @RequestParam Date endDate) {
        List<RoomDetails> availableRooms = bookingService.searchBookings(hotelId, startDate, endDate);
        return availableRooms.stream()
                .map(roomService::toDTO)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }
}
