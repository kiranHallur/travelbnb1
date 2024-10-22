package com.travelbnb.travelbnb.service;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.travelbnb.travelbnb.dto.BookingDto;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PDFServiceIMPL implements PDFService{


    @Override
    public boolean generatePDF(String fileName , BookingDto bookingDto ) {
        try {


            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Font tableFont = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);

            // Title
            Chunk bookingConfirmation = new Chunk("Booking Confirmation", font);
            document.add(bookingConfirmation);
            document.add(new Paragraph("\n"));

            // Table with Booking Details
            PdfPTable table = new PdfPTable(2); // 2 columns

            // Adding table headers
            table.addCell(new PdfPCell(new Phrase("Detail", tableFont)));
            table.addCell(new PdfPCell(new Phrase("Value", tableFont)));

            // Adding table data
            table.addCell(new PdfPCell(new Phrase("Guest Name", tableFont)));
            table.addCell(new PdfPCell(new Phrase(bookingDto.getName(), tableFont)));

            table.addCell(new PdfPCell(new Phrase("Guest Email", tableFont)));
            table.addCell(new PdfPCell(new Phrase(bookingDto.getEmail(), tableFont)));

            table.addCell(new PdfPCell(new Phrase("Guest Mobile", tableFont)));
            table.addCell(new PdfPCell(new Phrase(bookingDto.getMobile(), tableFont)));

            table.addCell(new PdfPCell(new Phrase("Total Nights", tableFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(bookingDto.getTotalNights()), tableFont)));

            table.addCell(new PdfPCell(new Phrase("Total Price", tableFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(bookingDto.getPrice()), tableFont)));

            // Adding table to document
            document.add(table);

            document.close();
            return true;




//without table
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream(fileName));
//
//            document.open();
//            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
//            Chunk bookingConfirmation = new Chunk("Booking Confirmation ", font);
//            Chunk guestName = new Chunk("Guest Name:" + bookingDto.getName(), font);
//            Chunk guestEmail = new Chunk("Guest Email:"+bookingDto.getEmail(),font);
//            Chunk guestMobile = new Chunk("Guest Mobile:"+bookingDto.getMobile(),font);
//            Chunk totalNights = new Chunk("TotalNights:" + bookingDto.getTotalNights(), font);
//            Chunk totalPrice = new Chunk("TotalPrice:" + bookingDto.getPrice(), font);
//
//
//            document.add(bookingConfirmation);
//            document.add(new Paragraph("\n"));
//            document.add(guestName);
//            document.add(new Paragraph("\n"));
//            document.add(guestEmail);
//            document.add(new Paragraph("\n"));
//            document.add(guestMobile);
//            document.add(new Paragraph("\n"));
//            document.add(totalNights);
//            document.add(new Paragraph("\n"));
//            document.add(totalPrice);
//            document.add(new Paragraph("\n"));
//
//            document.close();
//            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    }

