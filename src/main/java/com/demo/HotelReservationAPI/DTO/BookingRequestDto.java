package com.demo.HotelReservationAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {
    private Date startDate;
    private Date endDate;
    private Long customerId;
    private Long hotelId;
    private Long roomId;
}
