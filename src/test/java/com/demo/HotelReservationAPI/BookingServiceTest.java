package com.demo.HotelReservationAPI;

import com.demo.HotelReservationAPI.DTO.BookingRequestDto;
import com.demo.HotelReservationAPI.DTO.BookingResponseDto;
import com.demo.HotelReservationAPI.Entity.BookingDetails;
import com.demo.HotelReservationAPI.Entity.CustomerDetails;
import com.demo.HotelReservationAPI.Entity.HotelDetails;
import com.demo.HotelReservationAPI.Entity.RoomDetails;
import com.demo.HotelReservationAPI.Enum.BookingStatus;
import com.demo.HotelReservationAPI.Repository.BookingDetailsRepository;
import com.demo.HotelReservationAPI.Repository.CustomerDetailsRepository;
import com.demo.HotelReservationAPI.Repository.HotelDetailsRepository;
import com.demo.HotelReservationAPI.Repository.RoomDetailsRepository;
import com.demo.HotelReservationAPI.Service.BookingService;
import com.demo.HotelReservationAPI.Util.Helper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookingServiceTest {

    @Mock
    private BookingDetailsRepository bookingDetailsRepository;
    @Mock
    private HotelDetailsRepository hotelDetailsRepository;
    @Mock
    private CustomerDetailsRepository customerDetailsRepository;
    @Mock
    private RoomDetailsRepository roomDetailsRepository;

    @InjectMocks
    private BookingService bookingService;

    private BookingRequestDto bookingRequestDto;

    private BookingDetails bookingDetails;

    private HotelDetails hotelDetails;

    private CustomerDetails customerDetails;

    private RoomDetails roomDetails;

    private RoomDetails roomDetails2;

    private List<RoomDetails> roomDetailsList;

    private List<BookingDetails> bookingDetailsList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setStartDate("2024-05-07");
        bookingRequestDto.setEndDate("2024-05-10");
        bookingRequestDto.setRoomId(1L);
        bookingRequestDto.setHotelId(1L);
        bookingRequestDto.setCustomerId(1L);

        customerDetails = new CustomerDetails();
        customerDetails.setCustomerID(1L);

        roomDetails = new RoomDetails();
        roomDetails.setId(1L);

        roomDetails2 = new RoomDetails();
        roomDetails2.setId(2L);

        roomDetailsList = new ArrayList<>();
        roomDetailsList.add(roomDetails);
        roomDetailsList.add(roomDetails2);

        hotelDetails = new HotelDetails();
        hotelDetails.setHotelId(1L);
        hotelDetails.setRooms(roomDetailsList);



        Mockito.when(hotelDetailsRepository.findByHotelId(bookingRequestDto.getHotelId())).thenReturn(hotelDetails);
        Mockito.when(customerDetailsRepository.findByCustomerID(bookingRequestDto.getCustomerId())).thenReturn(customerDetails);
        Mockito.when(roomDetailsRepository.findById(bookingRequestDto.getRoomId())).thenReturn(roomDetails);

        bookingDetails = bookingService.toBookingDetails(bookingRequestDto);
        bookingDetails.setBookingId(1L);
        bookingDetails.setRoom(roomDetails);

        Mockito.when(bookingDetailsRepository.save(bookingDetails)).thenReturn(bookingDetails);

        bookingDetailsList = new ArrayList<>();
        bookingDetailsList.add(bookingDetails);
        customerDetails.setBookingList(bookingDetailsList);
        // any other properties needed in the request...
    }

    @Test
    void testAddBooking() {
        Boolean result = bookingService.addBooking(bookingRequestDto);

        verify(bookingDetailsRepository, times(1)).save(any(BookingDetails.class));
        assertEquals(true, result);
    }

    @Test
    void testGetAllBookings() {
        when(bookingDetailsRepository.findAll()).thenReturn(bookingDetailsList);

        List<BookingResponseDto> bookingResponseDtoList = bookingService.getAllBookings();

        assertEquals(1, bookingResponseDtoList.size());
        assertEquals(1L, bookingResponseDtoList.get(0).getBookingId());
        assertEquals(1L,bookingResponseDtoList.get(0).getCustomerId());
        assertEquals(1L,bookingResponseDtoList.get(0).getHotelId());
        assertEquals(1L,bookingResponseDtoList.get(0).getRoomId());
    }

    @Test
    void testCancelBooking() {
        Mockito.when(bookingDetailsRepository.findByBookingId(bookingDetails.getBookingId())).thenReturn(bookingDetails);

        ResponseEntity<Object> responseEntity = bookingService.cancelBooking(bookingDetails.getBookingId());
        BookingResponseDto bookingResponseDto = (BookingResponseDto) responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(BookingStatus.CANCEL,bookingResponseDto.getStatus());
    }

    @Test
    void testCancelBookingInvalidBookingId() {
        Mockito.when(bookingDetailsRepository.findByBookingId(2L)).thenReturn(null);

        ResponseEntity<Object> responseEntity = bookingService.cancelBooking(2L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    void testGetBookingByCustomerId() {
        Mockito.when(bookingDetailsRepository.findByCustomer_CustomerID(1L)).thenReturn(bookingDetailsList);

        ResponseEntity<Object> responseEntity = bookingService.getBookingsByCustomerId(1L);
        List<BookingResponseDto> bookingResponseDtoList = (List<BookingResponseDto>) responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, bookingResponseDtoList.size());
        assertEquals(1L, bookingResponseDtoList.get(0).getBookingId());
        assertEquals(1L, bookingResponseDtoList.get(0).getCustomerId());
        assertEquals(1L, bookingResponseDtoList.get(0).getHotelId());
        assertEquals(1L, bookingResponseDtoList.get(0).getRoomId());
    }

    @Test
    void testGetBookingByCustomerIdInvalidCustomerId() {
        Mockito.when(bookingDetailsRepository.findByCustomer_CustomerID(2L)).thenReturn(new ArrayList<>());
        ResponseEntity<Object> responseEntity = bookingService.getBookingsByCustomerId(2L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetBookingById() {
        Mockito.when(bookingDetailsRepository.findByBookingId(1L)).thenReturn(bookingDetails);

        ResponseEntity<Object> responseEntity = bookingService.getBookingById(1L);
        BookingResponseDto bookingResponseDto = (BookingResponseDto) responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, bookingResponseDto.getBookingId());
        assertEquals(1L, bookingResponseDto.getCustomerId());
        assertEquals(1L, bookingResponseDto.getHotelId());
        assertEquals(1L, bookingResponseDto.getRoomId());
    }

    @Test
    void testGetBookingByIdInvalidBookingId() {
        Mockito.when(bookingDetailsRepository.findByBookingId(2L)).thenReturn(null);

        ResponseEntity<Object> responseEntity = bookingService.getBookingById(2L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateBooking() {
        Mockito.when(bookingDetailsRepository.getById(bookingDetails.getBookingId())).thenReturn(bookingDetails);
        RoomDetails extraRoom = new RoomDetails();
        extraRoom.setId(2L);
        Mockito.when(roomDetailsRepository.findById(2L)).thenReturn(extraRoom);

        ResponseEntity<Object> responseEntity = bookingService.updateBooking(bookingDetails.getBookingId(), "2024-05-08", "2024-05-11", 2L);
        BookingResponseDto bookingResponseDto = (BookingResponseDto) responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2L, bookingResponseDto.getRoomId());
        assertEquals("2024-05-08", Helper.convertDateToString(bookingResponseDto.getStartDate()));
        assertEquals("2024-05-11", Helper.convertDateToString(bookingResponseDto.getEndDate()));
    }

    @Test
    void testSearchBooking() {
        Mockito.when(bookingDetailsRepository.findByHotel_HotelIdAndStartDateLessThanAndEndDateGreaterThan(1L,Helper.convertStringToDate("2024-05-10"), Helper.convertStringToDate("2024-05-07"))).thenReturn(bookingDetailsList);
        Mockito.when(roomDetailsRepository.findByHotel_HotelId(1L)).thenReturn(roomDetailsList);

        List<RoomDetails> availableRoomForBooking = bookingService.searchBookings(1L, "2024-05-07", "2024-05-10");
        assertEquals(1, availableRoomForBooking.size());
        assertEquals(2L, availableRoomForBooking.get(0).getId());
    }


}
