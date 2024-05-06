package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.DTO.BookingRequestDto;
import com.demo.HotelReservationAPI.DTO.BookingResponseDto;
import com.demo.HotelReservationAPI.Entity.BookingDetails;
import com.demo.HotelReservationAPI.Entity.RoomDetails;
import com.demo.HotelReservationAPI.Enum.BookingStatus;
import com.demo.HotelReservationAPI.Repository.BookingDetailsRepository;
import com.demo.HotelReservationAPI.Repository.CustomerDetailsRepository;
import com.demo.HotelReservationAPI.Repository.HotelDetailsRepository;
import com.demo.HotelReservationAPI.Repository.RoomDetailsRepository;
import com.demo.HotelReservationAPI.Util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private BookingDetailsRepository bookingDetailsRepository;

    private HotelDetailsRepository hotelDetailsRepository;

    private RoomDetailsRepository roomDetailsRepository;

    private CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    public BookingService(BookingDetailsRepository bookingDetailsRepository,
                          HotelDetailsRepository hotelDetailsRepository,
                          RoomDetailsRepository roomDetailsRepository,
                          CustomerDetailsRepository customerDetailsRepository) {
        this.bookingDetailsRepository = bookingDetailsRepository;
        this.hotelDetailsRepository = hotelDetailsRepository;
        this.roomDetailsRepository = roomDetailsRepository;
        this.customerDetailsRepository = customerDetailsRepository;
    }

    public BookingResponseDto toBookingDto(BookingDetails bookingDetails) {
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        bookingResponseDto.setBookingId(bookingDetails.getBookingId());

        bookingResponseDto.setStartDate(bookingDetails.getStartDate());
        bookingResponseDto.setEndDate(bookingDetails.getEndDate());
        bookingResponseDto.setTimeCreated(bookingDetails.getTimeCreated());

        bookingResponseDto.setHotelId(bookingDetails.getHotel().getHotelId());
        bookingResponseDto.setHotelName(bookingDetails.getHotel().getHotelName());
        bookingResponseDto.setRoomId(bookingDetails.getRoom().getId());
        bookingResponseDto.setRoomNumber(bookingDetails.getRoom().getRoomNumber());
        bookingResponseDto.setRoomType(bookingDetails.getRoom().getRoomType());
        bookingResponseDto.setCustomerId(bookingDetails.getCustomer().getCustomerID());
        bookingResponseDto.setCustomerName(bookingDetails.getCustomer().getFirstName() + " " + bookingDetails.getCustomer().getLastName());
        bookingResponseDto.setStatus(bookingDetails.getStatus());
        return bookingResponseDto;
    }

    protected BookingDetails toBookingDetails(BookingRequestDto bookingRequestDto) {
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setStartDate(Helper.convertStringToDate(bookingRequestDto.getStartDate()));
        bookingDetails.setEndDate(Helper.convertStringToDate(bookingRequestDto.getEndDate()));
        bookingDetails.setTimeCreated(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        bookingDetails.setHotel(hotelDetailsRepository.findByHotelId(bookingRequestDto.getHotelId()));
        bookingDetails.setRoom(roomDetailsRepository.findById(bookingRequestDto.getRoomId()));
        bookingDetails.setCustomer(customerDetailsRepository.findByCustomerID(bookingRequestDto.getCustomerId()));
        return bookingDetails;
    }

    public Boolean addBooking(BookingRequestDto bookingRequestDto) {
        BookingDetails bookingDetails = toBookingDetails(bookingRequestDto);
        bookingDetails.setStatus(BookingStatus.CONFIRMED);
        if (isBookingValid(bookingRequestDto)) {
            bookingDetailsRepository.save(bookingDetails);
            return true;
        } else {
            return false;
        }
    }

    public List<BookingResponseDto> getAllBookings() {
        List<BookingResponseDto> bookingResponseDtos = new ArrayList<>();
        List<BookingDetails> bookingDetailsList = bookingDetailsRepository.findAll();
        bookingDetailsList.forEach(booking -> {
            bookingResponseDtos.add(toBookingDto(booking));
        });
        return bookingResponseDtos;
    }

    public ResponseEntity<Object> cancelBooking(Long bookingId) {
       BookingDetails bookingDetails = bookingDetailsRepository.findByBookingId(bookingId);
       if (bookingDetails == null) {
           return ResponseEntity.notFound().build();
       } else {
           bookingDetails.setStatus(BookingStatus.CANCEL);
           return ResponseEntity.ok(toBookingDto(bookingDetailsRepository.save(bookingDetails)));
       }
    }

    public ResponseEntity<Object> getBookingsByCustomerId(Long customerId) {
        List<BookingDetails> bookingDetailsList = bookingDetailsRepository.findByCustomer_CustomerID(customerId);
        if (bookingDetailsList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<BookingResponseDto> bookingResponseDtos = new ArrayList<>();
            bookingDetailsList.forEach(booking -> {bookingResponseDtos.add(toBookingDto(booking));});
            return ResponseEntity.ok(bookingResponseDtos);
        }
    }

    public ResponseEntity<Object> getBookingById(Long bookingId) {
        BookingDetails bookingDetails = bookingDetailsRepository.findByBookingId(bookingId);
        if (bookingDetails == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(toBookingDto(bookingDetails));
        }
    }

    public ResponseEntity<Object> updateBooking(Long bookingId,
                                                String newStartDate,
                                                String newEndDate,
                                                Long newRoomId) {
        BookingDetails bookingDetails = bookingDetailsRepository.getById(bookingId);
        updateBookingStartDate(bookingDetails, newStartDate);
        updateBookingEndDate(bookingDetails, newEndDate);
        updateBookingRoom(bookingDetails, newRoomId);

        if (!isBookingValid(new BookingRequestDto(bookingDetails.getRoom().getId(), Helper.convertDateToString(bookingDetails.getStartDate()), Helper.convertDateToString(bookingDetails.getEndDate())))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This room is not available for this time period");
        }
        if (!isPeriodValid(bookingDetails.getStartDate(), bookingDetails.getEndDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date cannot be after end date");
        }
        if (BookingStatus.CANCEL.equals(bookingDetails.getStatus())) {
            bookingDetails.setStatus(BookingStatus.CONFIRMED);
        }
        return ResponseEntity.ok(toBookingDto(bookingDetailsRepository.save(bookingDetails)));
    }

    public boolean isPeriodValid(Date startDate, Date endDate) {
        return endDate.after(startDate);
    }

    public List<RoomDetails> searchBookings(Long hotelId,
                                            String startDate,
                                            String endDate) {
        List<BookingDetails> bookings = bookingDetailsRepository.findByHotel_HotelIdAndStartDateLessThanAndEndDateGreaterThan(hotelId, Helper.convertStringToDate(endDate), Helper.convertStringToDate(startDate));

        List<RoomDetails> bookedRooms = bookings.stream()
                .filter(booking -> booking.getStatus() != BookingStatus.CANCEL)
                .map(BookingDetails::getRoom)
                .collect(Collectors.toList());

        List<RoomDetails> allRoomsInHotel = roomDetailsRepository.findByHotel_HotelId(hotelId);

        List<RoomDetails> availableRooms = allRoomsInHotel.stream()
                .filter(room -> bookedRooms.stream().noneMatch(bookedRoom -> bookedRoom.getId().equals(room.getId())))
                .collect(Collectors.toList());
        return availableRooms;
    }

    private Boolean isBookingValid(BookingRequestDto bookingRequestDto) {
        Date startDate = Helper.convertStringToDate(bookingRequestDto.getStartDate());
        Date endDate = Helper.convertStringToDate(bookingRequestDto.getEndDate());

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid date format");
        }

        Long roomId = bookingRequestDto.getRoomId();
        List<BookingDetails> bookings = bookingDetailsRepository.findByRoomIdAndStartDateLessThanAndEndDateGreaterThan(roomId, endDate, startDate);
        return hasNoActiveBookings(bookings);
    }

    private boolean hasNoActiveBookings(List<BookingDetails> bookings) {
        return bookings.isEmpty() || bookings.stream().allMatch(b -> b.getStatus() == BookingStatus.CANCEL);
    }

    private void updateBookingStartDate(BookingDetails bookingDetails, String newStartDate) {
        if (newStartDate != null) {
            bookingDetails.setStartDate(Helper.convertStringToDate(newStartDate));
        }
    }

    private void updateBookingEndDate(BookingDetails bookingDetails, String newEndDate) {
        if (newEndDate != null) {
            bookingDetails.setEndDate(Helper.convertStringToDate(newEndDate));
        }
    }

    private void updateBookingRoom(BookingDetails bookingDetails, Long newRoomId) {
        if (newRoomId != null) {
            bookingDetails.setRoom(roomDetailsRepository.findById(newRoomId));
        }
    }
}
