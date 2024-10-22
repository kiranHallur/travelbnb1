package com.travelbnb.travelbnb.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ExcelPDFService {
    default void generateExcel(HttpServletResponse httpServletResponse) throws IOException {

    }

    void bookingExcelReport(HttpServletResponse response) throws IOException;
}
