package com.demo.HotelReservationAPI.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BOOKING_DETAILS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingDetails implements Serializable {
    @Id
    @SequenceGenerator(
            name = "BOOKING_DETAILS_SEQ",
            sequenceName = "BOOKING_DETAILS_SEQ",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "BOOKING_DETAILS_SEQ"
    )
    @Column(name = "BOOKING_ID")
    private Long bookingId;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerDetails customer;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private HotelDetails hotel;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private RoomDetails room;
}
