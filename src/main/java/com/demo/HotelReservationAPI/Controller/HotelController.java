package com.demo.HotelReservationAPI.Controller;

import com.demo.HotelReservationAPI.DTO.HotelRequestDto;
import com.demo.HotelReservationAPI.DTO.HotelResponseDto;
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
    public void addHotel(@RequestBody HotelRequestDto dto) {
        service.addHotel(dto);
    }

    @GetMapping("/getAllHotels")
    public List<HotelResponseDto> getAllHotels(){
        return service.getAllHotels();
    }


}
