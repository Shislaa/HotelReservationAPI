package com.demo.HotelReservationAPI.DTO;

import com.demo.HotelReservationAPI.Entity.BookingDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String zip;

    private String country;

    private List<BookingDetails> bookingList;

}
