package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.Repository.BookingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;
}
