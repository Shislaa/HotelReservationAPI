package com.demo.HotelReservationAPI.Repository;


import com.demo.HotelReservationAPI.Entity.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {
    List<BookingDetails> findByCustomer_CustomerID(Long customerId);

    List<BookingDetails> findByRoomIdAndStartDateLessThanAndEndDateGreaterThan(Long roomId, Date endDate, Date startDate);

    List<BookingDetails> findByHotel_HotelIdAndStartDateLessThanAndEndDateGreaterThan(Long hotelId, Date endDate , Date startDate);

    BookingDetails findByBookingId(Long bookingId);
}
