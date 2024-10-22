package com.travelbnb.travelbnb.controller;


import com.travelbnb.travelbnb.dto.BookingDto;
import com.travelbnb.travelbnb.entity.AppUser;
import com.travelbnb.travelbnb.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/Booking")
public class BookingController {
    private BookingService bookingService ;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //http://localhost:8080/api/v1/Booking/AddBooking?propertyId=1

    @PostMapping
    public ResponseEntity<BookingDto>CreateBooking(
            @RequestParam long propertyId,
            @AuthenticationPrincipal AppUser user ,
            @RequestBody BookingDto bookingDto

            ){
        BookingDto bookDto = bookingService.CreateBooking(propertyId,user,bookingDto);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }
}
