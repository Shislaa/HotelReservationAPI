package com.demo.HotelReservationAPI.Repository;


import com.demo.HotelReservationAPI.Entity.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {
    List<BookingDetails> findByCustomer_CustomerID(Long customerId);

    BookingDetails findByRoomIdAndStartDateAndEndDate (Long roomId, Date startDate, Date endDate);

    List<BookingDetails> findByHotel_HotelIdAndStartDateAndEndDate (Long hotelId, Date startDate, Date endDate);

    BookingDetails findByBookingId(Long bookingId);
}
