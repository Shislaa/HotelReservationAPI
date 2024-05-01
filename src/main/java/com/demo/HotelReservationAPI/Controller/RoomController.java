package com.demo.HotelReservationAPI.Controller;

import com.demo.HotelReservationAPI.DTO.RoomDTO;
import com.demo.HotelReservationAPI.Entity.BookingDetails;
import com.demo.HotelReservationAPI.Entity.HotelDetails;
import com.demo.HotelReservationAPI.Entity.RoomDetails;
import com.demo.HotelReservationAPI.Enum.RoomType;
import com.demo.HotelReservationAPI.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/addRoom")
    public void addRoom(
            @RequestParam(value = "roomNumber") String roomNumber,
            @RequestParam(value = "roomType") RoomType roomType,
            @RequestParam(value = "hotel") HotelDetails hotel
    ) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomNumber(roomNumber);
        roomDTO.setRoomType(roomType);
        roomDTO.setHotel(hotel);
        roomService.addRoom(roomDTO);
    }

    @GetMapping("/getAllRooms")
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

}
