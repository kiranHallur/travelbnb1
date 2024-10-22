package com.travelbnb.travelbnb.service;

import com.travelbnb.travelbnb.dto.BookingDto;

public interface PDFService  {
    boolean generatePDF(String s, BookingDto bookDto);
}
