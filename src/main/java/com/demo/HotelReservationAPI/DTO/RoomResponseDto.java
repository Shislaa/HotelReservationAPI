package com.demo.HotelReservationAPI.DTO;

import com.demo.HotelReservationAPI.Enum.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomResponseDto {
    private Long RoomId;

    private String roomNumber;

    private RoomType roomType;

    private Long hotelId;

//    private List<BookingDetails> bookingList;
}
