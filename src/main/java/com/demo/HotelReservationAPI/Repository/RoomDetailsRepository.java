package com.demo.HotelReservationAPI.Repository;

import com.demo.HotelReservationAPI.Entity.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Integer> {
}
