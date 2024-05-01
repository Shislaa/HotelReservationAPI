package com.demo.HotelReservationAPI.DTO;

import com.demo.HotelReservationAPI.Entity.BookingDetails;
import com.demo.HotelReservationAPI.Entity.RoomDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelDTO {

    private String hotelName;

    private String hotelAddress;

    private String hotelCity;

    private String hotelState;

    private String hotelCountry;

    private String hotelZip;

    private String hotelPhone;

    private String hotelEmail;

    private String ownerName;

    private String ownerPhone;

    private String ownerEmail;

    private List<RoomDetails> rooms;

    private List<BookingDetails> bookingList;
}
