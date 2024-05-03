package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.DTO.BookingRequestDto;
import com.demo.HotelReservationAPI.DTO.BookingResponseDto;
import com.demo.HotelReservationAPI.Entity.BookingDetails;
import com.demo.HotelReservationAPI.Enum.BookingStatus;
import com.demo.HotelReservationAPI.Repository.BookingDetailsRepository;
import com.demo.HotelReservationAPI.Repository.CustomerDetailsRepository;
import com.demo.HotelReservationAPI.Repository.HotelDetailsRepository;
import com.demo.HotelReservationAPI.Repository.RoomDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        bookingDetails.setHotel(hotelDetailsRepository.findByHotelId(bookingRequestDto.getHotelId()));
        bookingDetails.setRoom(roomDetailsRepository.findById(bookingRequestDto.getRoomId()));
        bookingDetails.setCustomer(customerDetailsRepository.findByCustomerID(bookingRequestDto.getCustomerId()));
        return bookingDetails;
    }

    public void addBooking(BookingRequestDto bookingRequestDto) {
        BookingDetails bookingDetails = toBookingDetails(bookingRequestDto);
        bookingDetails.setStatus(BookingStatus.CONFIRMED);
        bookingDetailsRepository.save(bookingDetails);
    }

    public List<BookingResponseDto> getAllBookings() {
        List<BookingResponseDto> bookingResponseDtos = new ArrayList<>();
        List<BookingDetails> bookingDetailsList = bookingDetailsRepository.findAll();
        bookingDetailsList.forEach(booking -> {
            bookingResponseDtos.add(toBookingDto(booking));
        });
        return bookingResponseDtos;
    }

    public void cancelBooking(Long bookingId) {
       BookingDetails bookingDetails = bookingDetailsRepository.getById(bookingId);
       bookingDetails.setStatus(BookingStatus.CANCEL);
       bookingDetailsRepository.save(bookingDetails);
    }

    public List<BookingResponseDto> getBookingsByCustomerId(Long customerId) {
        List<BookingDetails> bookingDetailsList = bookingDetailsRepository.findByCustomer_CustomerID(customerId);
        List<BookingResponseDto> bookingResponseDtos = new ArrayList<>();
        bookingDetailsList.forEach(booking -> {bookingResponseDtos.add(toBookingDto(booking));});
        return bookingResponseDtos;
    }

}
