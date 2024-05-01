package com.demo.HotelReservationAPI.DTO;

import com.demo.HotelReservationAPI.Entity.BookingDetails;
import com.demo.HotelReservationAPI.Entity.HotelDetails;
import com.demo.HotelReservationAPI.Enum.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDTO {
    private Long RoomId;

    private String roomNumber;

    private RoomType roomType;

    private HotelDetails hotel;

    private List<BookingDetails> bookingList;
}
