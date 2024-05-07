package com.demo.HotelReservationAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    private String startDate;
    private String endDate;
    private Long customerId;
    private Long hotelId;
    private Long roomId;

    public BookingRequestDto(Long newRoomId, String newStart, String newEnd) {
        this.roomId = newRoomId;
        this.startDate = newStart;
        this.endDate = newEnd;
    }
}
