package com.demo.HotelReservationAPI.Controller;

import com.demo.HotelReservationAPI.DTO.RoomRequestDto;
import com.demo.HotelReservationAPI.DTO.RoomResponseDto;
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
    public void addRoom(@RequestParam(value = "roomNumber") String roomNumber,
                        @RequestParam(value = "roomType") RoomType roomType,
                        @RequestParam(value = "hotelId") Long hotelId) {
        RoomRequestDto roomRequestDto = new RoomRequestDto();
        roomRequestDto.setRoomNumber(roomNumber);
        roomRequestDto.setRoomType(roomType);
        roomRequestDto.setHotelId(hotelId);
        roomService.addRoom(roomRequestDto);
    }

    @GetMapping("/getAllRooms")
    public List<RoomResponseDto> getAllRooms() {
        return roomService.getAllRooms();
    }

}
