package com.travelbnb.travelbnb.service;

import com.travelbnb.travelbnb.dto.BookingDto;
import com.travelbnb.travelbnb.entity.AppUser;

public interface BookingService {
    BookingDto CreateBooking(long propertyId, AppUser user, BookingDto bookingDto);
}
