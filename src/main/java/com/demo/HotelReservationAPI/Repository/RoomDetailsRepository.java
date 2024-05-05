package com.demo.HotelReservationAPI.Repository;

import com.demo.HotelReservationAPI.Entity.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Integer> {
    RoomDetails findById(Long roomId);

    List<RoomDetails> findByHotel_HotelId(Long hotelId);
}
