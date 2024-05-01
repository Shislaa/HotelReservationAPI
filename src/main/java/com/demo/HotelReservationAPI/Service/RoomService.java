package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.DTO.RoomDTO;
import com.demo.HotelReservationAPI.Entity.RoomDetails;
import com.demo.HotelReservationAPI.Repository.RoomDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private RoomDetailsRepository repository;

    @Autowired
    public RoomService (RoomDetailsRepository repository) {
        this.repository = repository;
    }

    public RoomDTO toDTO(RoomDetails roomDetails) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomId(roomDetails.getId());
        roomDTO.setRoomNumber(roomDetails.getRoomNumber());
        roomDTO.setRoomType(roomDetails.getRoomType());
        roomDTO.setHotel(roomDetails.getHotel());
        roomDTO.setBookingList(roomDetails.getBookingList());
        return roomDTO;
    }

    public RoomDetails toEntity(RoomDTO roomDTO) {
        RoomDetails roomDetails = new RoomDetails();
        roomDetails.setRoomNumber(roomDTO.getRoomNumber());
        roomDetails.setRoomType(roomDTO.getRoomType());
        roomDetails.setHotel(roomDTO.getHotel());
        roomDetails.setBookingList(roomDTO.getBookingList());
        return roomDetails;
    }

    public void addRoom(RoomDTO dto) {
        repository.save(toEntity(dto));
    }

    public List<RoomDTO> getAllRooms() {
        List<RoomDetails> rooms = repository.findAll();
        List<RoomDTO> roomDTOs = new ArrayList<RoomDTO>();
        rooms.forEach(room -> roomDTOs.add(toDTO(room)));
        return roomDTOs;
    }
}
