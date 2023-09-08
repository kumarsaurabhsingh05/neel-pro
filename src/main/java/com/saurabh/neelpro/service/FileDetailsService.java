package com.saurabh.neelpro.service;

import com.saurabh.neelpro.entity.FileDetails;
import com.saurabh.neelpro.repository.FileDetailRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FileDetailsService {

    @Autowired
    private FileDetailRepository fileDetailsRepository;

    public void generateExcel() {
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream("file_details.xlsx")) {

            Sheet sheet = workbook.createSheet("File Details");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name of Person");
            headerRow.createCell(2).setCellValue("Local Date");
            headerRow.createCell(3).setCellValue("Local Time");

            // Populate data rows
            int rowIdx = 1;
            for (FileDetails fileDetail : fileDetailsList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(fileDetail.getId());
                row.createCell(1).setCellValue(fileDetail.getNameOfPerson());
                row.createCell(2).setCellValue(fileDetail.getLocalDate().toString());
                row.createCell(3).setCellValue(fileDetail.getLocalTime().toString());
            }

            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
