package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.Repository.RoomDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

}
