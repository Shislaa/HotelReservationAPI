package com.demo.HotelReservationAPI.Entity;

import com.demo.HotelReservationAPI.Enum.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ROOM_DETAILS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDetails {
    @Id
    @SequenceGenerator(
            name = "ROOM_DETAILS_SEQ",
            sequenceName = "ROOM_DETAILS_SEQ",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ROOM_DETAILS_SEQ"
    )
    @Column(name = "ROOM_ID")
    private Long id;

    @Column(name = "ROOM_NUMBER")
    private String roomNumber;

    @Column(name = "ROOM_TYPE")
    private RoomType roomType;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private HotelDetails hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<BookingDetails> bookingList;
}
