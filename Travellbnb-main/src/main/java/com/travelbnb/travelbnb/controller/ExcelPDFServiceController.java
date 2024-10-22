package com.travelbnb.travelbnb.controller;


import com.travelbnb.travelbnb.service.ExcelPDFService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/excel")
public class ExcelPDFServiceController {
    private ExcelPDFService pdfService ;

    public ExcelPDFServiceController(ExcelPDFService pdfService) {
        this.pdfService = pdfService;
    }

    //http://localhost:8080/api/v1/excel/generateExcel

    @GetMapping("/generateExcel")
    public void generateExcelReport(HttpServletResponse httpServletResponse ) throws IOException {
        httpServletResponse.setContentType("application/octet-stream");
        String headerKey= "Content-Disposition";
        String headerValue= "attachment;filename=User-confirmation.xls";

        httpServletResponse.setHeader(headerKey,headerValue);
        pdfService.generateExcel(httpServletResponse);
    }

//http://localhost:8080/api/v1/excel/bookingExcelReport
    @GetMapping("/bookingExcelReport")
    public void generateBookingExcelReport(HttpServletResponse response )throws Exception{
        response.setContentType("application/octet-stream");
        String headerKey="Content-Disposition";
        String headerValue= "attachment;filename=Booking-confirmation.xls";
        response.setHeader(headerKey,headerValue);
        pdfService.bookingExcelReport(response);
    }
}
