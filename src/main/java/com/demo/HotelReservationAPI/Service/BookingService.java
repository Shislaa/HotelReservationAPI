package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.DTO.BookingRequestDto;
import com.demo.HotelReservationAPI.DTO.BookingResponseDto;
import com.demo.HotelReservationAPI.DTO.RoomResponseDto;
import com.demo.HotelReservationAPI.Entity.BookingDetails;
import com.demo.HotelReservationAPI.Entity.RoomDetails;
import com.demo.HotelReservationAPI.Enum.BookingStatus;
import com.demo.HotelReservationAPI.Repository.BookingDetailsRepository;
import com.demo.HotelReservationAPI.Repository.CustomerDetailsRepository;
import com.demo.HotelReservationAPI.Repository.HotelDetailsRepository;
import com.demo.HotelReservationAPI.Repository.RoomDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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
        bookingDetails.setStartDate(bookingRequestDto.getStartDate());
        bookingDetails.setEndDate(bookingRequestDto.getEndDate());
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

    public BookingResponseDto cancelBooking(Long bookingId) {
       BookingDetails bookingDetails = bookingDetailsRepository.getById(bookingId);
       bookingDetails.setStatus(BookingStatus.CANCEL);
       return toBookingDto(bookingDetailsRepository.save(bookingDetails));
    }

    public List<BookingResponseDto> getBookingsByCustomerId(Long customerId) {
        List<BookingDetails> bookingDetailsList = bookingDetailsRepository.findByCustomer_CustomerID(customerId);
        List<BookingResponseDto> bookingResponseDtos = new ArrayList<>();
        bookingDetailsList.forEach(booking -> {bookingResponseDtos.add(toBookingDto(booking));});
        return bookingResponseDtos;
    }

    private Boolean isBookingValid(BookingRequestDto bookingRequestDto) {
        Date startDate = bookingRequestDto.getStartDate();
        Date endDate = bookingRequestDto.getEndDate();
        Long roomId = bookingRequestDto.getRoomId();
        BookingDetails booking = bookingDetailsRepository.findByRoomIdAndStartDateAndEndDate(roomId, startDate, endDate);
        return booking == null;
    }

    public BookingResponseDto getBookingById(Long bookingId) {
        return toBookingDto(bookingDetailsRepository.findByBookingId(bookingId));
    }

    public BookingResponseDto updateBooking(Long bookingId,
                                            Date newStartDate,
                                            Date newEndDate,
                                            Long newRoomId) {
        BookingDetails bookingDetails = bookingDetailsRepository.getById(bookingId);
        if (newStartDate != null) {
            bookingDetails.setStartDate(newStartDate);
        }
        if (newEndDate != null) {
            bookingDetails.setEndDate(newEndDate);
        }
        if (newRoomId != null) {
            bookingDetails.setRoom(roomDetailsRepository.findById(newRoomId));
        }
        return toBookingDto(bookingDetailsRepository.save(bookingDetails));
    }

    public List<RoomDetails> searchBookings(Long hotelId,
                                            Date startDate,
                                            Date endDate) {
        List<BookingDetails> bookings = bookingDetailsRepository.findByHotel_HotelIdAndStartDateAndEndDate(hotelId, startDate, endDate);

        List<RoomDetails> bookedRooms = bookings.stream()
                .map(BookingDetails::getRoom)
                .collect(Collectors.toList());

        List<RoomDetails> allRoomsInHotel = roomDetailsRepository.findByHotel_HotelId(hotelId);

        List<RoomDetails> availableRooms = allRoomsInHotel.stream()
                .filter(room -> bookedRooms.stream().noneMatch(bookedRoom -> bookedRoom.getId().equals(room.getId())))
                .collect(Collectors.toList());
        return availableRooms;
    }



}
