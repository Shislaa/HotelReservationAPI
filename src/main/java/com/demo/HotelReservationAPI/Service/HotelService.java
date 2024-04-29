package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.Repository.HotelDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    @Autowired
    HotelDetailsRepository hotelDetailsRepository;
}
