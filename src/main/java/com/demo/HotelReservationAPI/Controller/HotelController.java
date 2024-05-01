package com.demo.HotelReservationAPI.Controller;

import com.demo.HotelReservationAPI.DTO.HotelDTO;
import com.demo.HotelReservationAPI.Entity.BookingDetails;
import com.demo.HotelReservationAPI.Entity.RoomDetails;
import com.demo.HotelReservationAPI.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    private HotelService service;

    @Autowired
    public HotelController(HotelService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void addHotel(
                @RequestParam(value = "hotelName") String hotelName,
                @RequestParam(value = "hotelAddress") String hotelAddress,
                @RequestParam(value = "hotelCity") String hotelCity,
                @RequestParam(value = "hotelState") String hotelState,
                @RequestParam(value = "hotelCountry") String hotelCountry,
                @RequestParam(value = "hotelZip") String hotelZip,
                @RequestParam(value = "hotelPhone") String hotelPhone,
                @RequestParam(value = "hotelEmail") String hotelEmail,
                @RequestParam(value = "ownerName") String ownerName,
                @RequestParam(value = "ownerPhone") String ownerPhone,
                @RequestParam(value = "ownerEmail") String ownerEmail,
                @RequestParam(value = "rooms") List<RoomDetails> rooms,
                @RequestParam(value = "bookingList", required = false) List<BookingDetails> bookingList) {
        HotelDTO dto = new HotelDTO();
        dto.setHotelName(hotelName);
        dto.setHotelAddress(hotelAddress);
        dto.setHotelCity(hotelCity);
        dto.setHotelState(hotelState);
        dto.setHotelCountry(hotelCountry);
        dto.setHotelZip(hotelZip);
        dto.setHotelPhone(hotelPhone);
        dto.setHotelEmail(hotelEmail);
        dto.setOwnerName(ownerName);
        dto.setOwnerPhone(ownerPhone);
        dto.setOwnerEmail(ownerEmail);
        dto.setRooms(rooms);
        dto.setBookingList(bookingList);

        service.addHotel(dto);
    }

    @GetMapping("/getAllHotels")
    public List<HotelDTO> getAllHotels(){
        return service.getAllHotels();
    }


}
