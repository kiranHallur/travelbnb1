package com.travelbnb.travelbnb.repository;

import com.travelbnb.travelbnb.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}