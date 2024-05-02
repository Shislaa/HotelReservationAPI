package com.demo.HotelReservationAPI.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "CUSTOMER_DETAILS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDetails implements Serializable {
    @Id
    @SequenceGenerator(
            name = "CUSTOMER_DETAILS_SEQ",
            sequenceName = "CUSTOMER_DETAILS_SEQ",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "CUSTOMER_DETAILS_SEQ"
    )
    @Column(name = "CUSTOMER_ID")
    private Long customerID;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name ="PHONE")
    private String phone;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIP")
    private String zip;

    @Column(name = "COUNTRY")
    private String country;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<BookingDetails> bookingList;
}
