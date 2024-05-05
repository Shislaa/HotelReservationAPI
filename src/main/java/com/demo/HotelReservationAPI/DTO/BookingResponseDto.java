package com.demo.HotelReservationAPI.DTO;

import com.demo.HotelReservationAPI.Enum.BookingStatus;
import com.demo.HotelReservationAPI.Enum.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {
    private Long bookingId;
    private Date startDate;
    private Date endDate;
    private Date timeCreated;
    private BookingStatus status;
    private Long customerId;
    private String customerName;
    private Long hotelId;
    private String hotelName;
    private Long roomId;
    private String roomNumber;
    private RoomType roomType;
}
