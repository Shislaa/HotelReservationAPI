package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.DTO.HotelRequestDto;
import com.demo.HotelReservationAPI.DTO.HotelResponseDto;
import com.demo.HotelReservationAPI.DTO.RoomRequestDto;
import com.demo.HotelReservationAPI.DTO.RoomResponseDto;
import com.demo.HotelReservationAPI.Entity.HotelDetails;
import com.demo.HotelReservationAPI.Entity.RoomDetails;
import com.demo.HotelReservationAPI.Repository.HotelDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {

    private HotelDetailsRepository repository;

    private RoomService roomService;

    @Autowired
    public HotelService(HotelDetailsRepository repository,
                        RoomService roomService) {
        this.repository = repository;
        this.roomService = roomService;
    }

    public HotelResponseDto toDTO(HotelDetails hotelDetails) {
        HotelResponseDto dto = new HotelResponseDto();
        dto.setHotelId(hotelDetails.getHotelId());
        dto.setHotelCity(hotelDetails.getHotelCity());
        dto.setHotelName(hotelDetails.getHotelName());
        dto.setHotelAddress(hotelDetails.getHotelAddress());
        dto.setHotelPhone(hotelDetails.getHotelPhone());
        dto.setHotelEmail(hotelDetails.getHotelEmail());
        dto.setHotelState(hotelDetails.getHotelState());
        dto.setHotelCountry(hotelDetails.getHotelCountry());
        dto.setHotelZip(hotelDetails.getHotelZip());
        dto.setOwnerName(hotelDetails.getOwnerName());
        dto.setOwnerEmail(hotelDetails.getOwnerEmail());
        dto.setOwnerPhone(hotelDetails.getOwnerPhone());
        List<RoomResponseDto> roomResponseDtoList = new ArrayList<>();
        hotelDetails.getRooms().forEach(room -> {
            roomResponseDtoList.add(roomService.toDTO(room));});
        dto.setRooms(roomResponseDtoList);
//        dto.setBookingList(hotelDetails.getBookingList());
        return dto;
    }

    public HotelDetails toEntity(HotelRequestDto dto) {
        HotelDetails hotelDetails = new HotelDetails();
        hotelDetails.setHotelCity(dto.getHotelCity());
        hotelDetails.setHotelName(dto.getHotelName());
        hotelDetails.setHotelAddress(dto.getHotelAddress());
        hotelDetails.setHotelPhone(dto.getHotelPhone());
        hotelDetails.setHotelEmail(dto.getHotelEmail());
        hotelDetails.setHotelState(dto.getHotelState());
        hotelDetails.setHotelCountry(dto.getHotelCountry());
        hotelDetails.setHotelZip(dto.getHotelZip());
        hotelDetails.setOwnerName(dto.getOwnerName());
        hotelDetails.setOwnerEmail(dto.getOwnerEmail());
        hotelDetails.setOwnerPhone(dto.getOwnerPhone());
        List<RoomDetails> roomDetailsList = new ArrayList<>();
//        dto.getRooms().forEach(room -> {roomDetailsList.add(roomService.toEntity(room));});
        hotelDetails.setRooms(roomDetailsList);
//        hotelDetails.setBookingList(dto.getBookingList());
        return hotelDetails;
    }

    public void addHotel(HotelRequestDto dto) {
        HotelDetails hotelDetails = repository.save(toEntity(dto));
        Long hotelId = hotelDetails.getHotelId();
        List<RoomRequestDto> roomRequestDtoList = dto.getRooms();
        roomRequestDtoList.forEach(room -> {
            room.setHotelId(hotelId);
            roomService.addRoom(room);
        });
    }

    public List<HotelResponseDto> getAllHotels() {
        List<HotelDetails> hotelList = repository.findAll();
        List<HotelResponseDto> dtoList = new ArrayList<>();
        hotelList.forEach(hotelDetails -> dtoList.add(toDTO(hotelDetails)));
        return dtoList;
    }

}
