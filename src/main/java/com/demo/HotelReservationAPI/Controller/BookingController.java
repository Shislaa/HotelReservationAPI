package com.demo.HotelReservationAPI.Controller;

import com.demo.HotelReservationAPI.DTO.BookingRequestDto;
import com.demo.HotelReservationAPI.DTO.BookingResponseDto;
import com.demo.HotelReservationAPI.Entity.RoomDetails;
import com.demo.HotelReservationAPI.Service.BookingService;
import com.demo.HotelReservationAPI.Service.RoomService;
import com.demo.HotelReservationAPI.Util.Helper;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Add a new booking", notes = "Create a new booking. The dates should be provided in 'YYYY-MM-DD' format.")
    @PostMapping("/addBooking")
    public ResponseEntity<String> addBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        Date startDate = Helper.convertStringToDate(bookingRequestDto.getStartDate());
        Date endDate = Helper.convertStringToDate(bookingRequestDto.getEndDate());

        if (startDate == null || endDate == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format");
        }

        if (!bookingService.isPeriodValid(startDate,endDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date cannot be after end date");
        }

        Boolean isBookingSaved = bookingService.addBooking(bookingRequestDto);
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
    public ResponseEntity<Object> cancelBooking(@RequestParam Long bookingId) {
        return bookingService.cancelBooking(bookingId);
    }

    @GetMapping("/getBookingByCustomerId")
    public ResponseEntity<Object> getBookingByCustomerId(@RequestParam Long customerId) {
        return bookingService.getBookingsByCustomerId(customerId);
    }

    @GetMapping("/getBookingById")
    public ResponseEntity<Object> getBookingById(@RequestParam Long bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @ApiOperation(value = "Update Existing Booking", notes = "Update information of existing booking for changing start date, end date and roomtype\n" +
            "The dates should be provided in 'YYYY-MM-DD' format.")
    @PostMapping("/updateBooking")
    public ResponseEntity<Object> updateBookingDetails(@RequestParam Long bookingId,
                                                   @RequestParam(required = false) String newStartDate,
                                                   @RequestParam(required = false) String newEndDate,
                                                   @RequestParam(required = false) Long newRoomId ) {
        return bookingService.updateBooking(bookingId, newStartDate, newEndDate, newRoomId);
    }

    @ApiOperation(value = "Search for available booking", notes = "Search for available room for designated hotel within a period\n" +
            "The dates should be provided in 'YYYY-MM-DD' format.")
    @GetMapping("/searchForBooking")
    public ResponseEntity<Object> searchForAvailableRooms(@RequestParam Long hotelId,
                                                         @RequestParam String startDate,
                                                         @RequestParam String endDate) {

        Date startDt = Helper.convertStringToDate(startDate);
        Date endDt = Helper.convertStringToDate(endDate);

        if (startDt == null || endDt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format");
        }

        if (!bookingService.isPeriodValid(startDt,endDt)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date cannot be after end date");
        }

        List<RoomDetails> availableRooms = bookingService.searchBookings(hotelId, startDate, endDate);
        return ResponseEntity.ok(availableRooms.stream()
                .map(roomService::toDTO)
                .collect(Collectors.toList()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }
}
