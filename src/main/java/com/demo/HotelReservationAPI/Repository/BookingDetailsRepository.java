package com.demo.HotelReservationAPI.Repository;


import com.demo.HotelReservationAPI.Entity.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {
}
