package com.thomassujay.clinicdata;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExcelWriter {

    public static void main(String[] args) {
        // Get the scraped clinic data
        List<String[]> clinicData = ClinicScraper.scrapeClinicData();

        // Sort clinicData by clinic name (index 0)
        Collections.sort(clinicData, new Comparator<String[]>() {
            @Override
            public int compare(String[] c1, String[] c2) {
                return c1[0].compareTo(c2[0]);
            }
        });

        // Create a workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clinic Data");

        // Create the header row
        String[] headers = {"Clinic Name", "Address", "Contact", "Website"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Write the sorted data to the Excel sheet
        int rowNum = 1;
        for (String[] clinic : clinicData) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < clinic.length; i++) {
                row.createCell(i).setCellValue(clinic[i]);
            }
        }

        // Auto-size columns for better readability
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Save the Excel file
        File excelFile = new File("clinics.xlsx");
        try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Excel file created and sorted successfully!");

            // Open the Excel file
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(excelFile);
            } else {
                System.out.println("Desktop is not supported on this system.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
