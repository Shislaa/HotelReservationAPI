package com.demo.HotelReservationAPI.Repository;

import com.demo.HotelReservationAPI.Entity.HotelDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelDetailsRepository extends JpaRepository<HotelDetails,Long> {
    HotelDetails findByHotelId(Long hotelId);
}
