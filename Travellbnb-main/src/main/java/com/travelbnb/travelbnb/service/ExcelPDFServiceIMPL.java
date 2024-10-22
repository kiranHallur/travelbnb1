package com.travelbnb.travelbnb.service;

import com.travelbnb.travelbnb.dto.BookingDto;
import com.travelbnb.travelbnb.entity.AppUser;
import com.travelbnb.travelbnb.entity.Booking;
import com.travelbnb.travelbnb.repository.AppUserRepository;
import com.travelbnb.travelbnb.repository.BookingRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelPDFServiceIMPL implements ExcelPDFService{
    private AppUserRepository appUserRepository ;
    private BookingRepository bookingRepository ;

    public ExcelPDFServiceIMPL(AppUserRepository appUserRepository, BookingRepository bookingRepository) {
        this.appUserRepository = appUserRepository;
        this.bookingRepository = bookingRepository;
    }


    public void generateExcel(HttpServletResponse response) throws IOException {
        List<AppUser> allBooking = appUserRepository.findAll();
        HSSFWorkbook document = new HSSFWorkbook();

        HSSFSheet bookingConfirmation = document.createSheet("User InFormation");
        HSSFRow row = bookingConfirmation.createRow(0);

        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Email");
        row.createCell(2).setCellValue("Name");
        row.createCell(3).setCellValue("Password");
        row.createCell(4).setCellValue("Role");
        row.createCell(5).setCellValue("Username");

        int dataRowIndex = 1;

        for (AppUser appUser    : allBooking){
            HSSFRow dataRow = bookingConfirmation.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(appUser.getId());
            dataRow.createCell(1).setCellValue(appUser.getEmail());
            dataRow.createCell(2).setCellValue(appUser.getName());
            dataRow.createCell(3).setCellValue(appUser.getPassword());
            dataRow.createCell(4).setCellValue(appUser.getRole());
            dataRow.createCell(5).setCellValue(appUser.getUsername());

            dataRowIndex ++;
        }


        ServletOutputStream outputStream = response.getOutputStream();
        document.write(outputStream);
        document.close();
        outputStream.close();
    }




    @Override
    public void bookingExcelReport(HttpServletResponse response) throws IOException {
        List<Booking> allBooking = bookingRepository.findAll();
        HSSFWorkbook bookingDocument = new HSSFWorkbook();

        HSSFSheet bookingConfirmation = bookingDocument.createSheet("User InFormation");
        HSSFRow row = bookingConfirmation.createRow(0);

        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Email");
        row.createCell(2).setCellValue("Mobile");
        row.createCell(3).setCellValue("Name");
        row.createCell(4).setCellValue("Price");
        row.createCell(5).setCellValue("TotalNights");

        int dataRowIndex = 1;

        for (Booking booking     : allBooking){
            HSSFRow dataRow = bookingConfirmation.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(booking.getId());
            dataRow.createCell(1).setCellValue(booking.getEmail());
            dataRow.createCell(2).setCellValue(booking.getMobile());
            dataRow.createCell(3).setCellValue(booking.getName());
            dataRow.createCell(4).setCellValue(booking.getPrice());
            dataRow.createCell(5).setCellValue(booking.getTotalNights());

            dataRowIndex ++;
        }


        ServletOutputStream outputStream = response.getOutputStream();
        bookingDocument.write(outputStream);
        bookingDocument.close();
        outputStream.close();
    }
    }

