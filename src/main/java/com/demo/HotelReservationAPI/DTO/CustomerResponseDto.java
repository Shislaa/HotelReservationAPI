package com.demo.HotelReservationAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private List<BookingResponseDto> bookings;
}
