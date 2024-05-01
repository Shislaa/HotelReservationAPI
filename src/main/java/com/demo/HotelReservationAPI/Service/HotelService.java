package com.demo.HotelReservationAPI.Service;

import com.demo.HotelReservationAPI.DTO.HotelDTO;
import com.demo.HotelReservationAPI.Entity.HotelDetails;
import com.demo.HotelReservationAPI.Repository.HotelDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {

    private HotelDetailsRepository repository;

    @Autowired
    public HotelService(HotelDetailsRepository repository) {
        this.repository = repository;
    }

    public HotelDTO toDTO(HotelDetails hotelDetails) {
        HotelDTO dto = new HotelDTO();
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
        dto.setRooms(hotelDetails.getRooms());
        dto.setBookingList(hotelDetails.getBookingList());
        return dto;
    }

    public HotelDetails toEntity(HotelDTO dto) {
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
        hotelDetails.setRooms(dto.getRooms());
        hotelDetails.setBookingList(dto.getBookingList());
        return hotelDetails;
    }

    public void addHotel(HotelDTO dto) {
        HotelDetails hotelDetails = toEntity(dto);
        repository.save(hotelDetails);
    }

    public List<HotelDTO> getAllHotels() {
        List<HotelDetails> hotelList = repository.findAll();
        List<HotelDTO> dtoList = new ArrayList<>();
        hotelList.forEach(hotelDetails -> dtoList.add(toDTO(hotelDetails)));
        return dtoList;
    }

}
