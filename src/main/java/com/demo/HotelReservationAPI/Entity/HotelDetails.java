package com.demo.HotelReservationAPI.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "HOTEL_DETAILS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelDetails {
    @Id
    @SequenceGenerator(
            name = "HOTEL_DETAILS_SEQ",
            sequenceName = "HOTEL_DETAILS_SEQ",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "HOTEL_DETAILS_SEQ"
    )
    @Column(name = "HOTEL_ID")
    private Long hotelId;

    @Column(name = "HOTEL_NAME")
    private String hotelName;

    @Column(name = "HOTEL_ADDRESS")
    private String hotelAddress;

    @Column(name = "CITY")
    private String hotelCity;

    @Column(name = "STATE")
    private String hotelState;

    @Column(name = "COUNTRY")
    private String hotelCountry;

    @Column(name = "ZIP_CODE")
    private String hotelZip;

    @Column(name = "HOTEL_PHONE")
    private String hotelPhone;

    @Column(name = "HOTEL_EMAIL")
    private String hotelEmail;

    @Column(name = "OWNER_NAME")
    private String ownerName;

    @Column(name = "OWNER_PHONE")
    private String ownerPhone;

    @Column(name = "OWNER_EMAIL")
    private String ownerEmail;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<RoomDetails> rooms;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<BookingDetails> bookingList;
}
