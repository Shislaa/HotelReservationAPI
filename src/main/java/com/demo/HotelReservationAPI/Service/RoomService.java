package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.DTO.RoomRequestDto;
import com.demo.HotelReservationAPI.DTO.RoomResponseDto;
import com.demo.HotelReservationAPI.Entity.RoomDetails;
import com.demo.HotelReservationAPI.Repository.HotelDetailsRepository;
import com.demo.HotelReservationAPI.Repository.RoomDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private RoomDetailsRepository repository;

    private HotelDetailsRepository hotelDetailsRepository;

    @Autowired
    public RoomService (RoomDetailsRepository repository,
                        HotelDetailsRepository hotelDetailsRepository) {
        this.repository = repository;
        this.hotelDetailsRepository = hotelDetailsRepository;
    }

    public RoomResponseDto toDTO(RoomDetails roomDetails) {
        RoomResponseDto roomResponseDto = new RoomResponseDto();
        roomResponseDto.setRoomId(roomDetails.getId());
        roomResponseDto.setRoomNumber(roomDetails.getRoomNumber());
        roomResponseDto.setRoomType(roomDetails.getRoomType());
        roomResponseDto.setHotelId(roomDetails.getHotel().getHotelId());
//        roomDTO.setBookingList(roomDetails.getBookingList());
        return roomResponseDto;
    }

    public RoomDetails toEntity(RoomRequestDto requestDto) {
        RoomDetails roomDetails = new RoomDetails();
        roomDetails.setRoomNumber(requestDto.getRoomNumber());
        roomDetails.setRoomType(requestDto.getRoomType());
        roomDetails.setHotel(hotelDetailsRepository.findByHotelId(requestDto.getHotelId()));
        return roomDetails;
    }

    public void addRoom(RoomRequestDto dto) {
        repository.save(toEntity(dto));
    }

    public List<RoomResponseDto> getAllRooms() {
        List<RoomDetails> rooms = repository.findAll();
        List<RoomResponseDto> roomResponseDtos = new ArrayList<RoomResponseDto>();
        rooms.forEach(room -> roomResponseDtos.add(toDTO(room)));
        return roomResponseDtos;
    }

    public RoomResponseDto findRoomById(Long id) {
        return toDTO(repository.findById(id));
    }
}
