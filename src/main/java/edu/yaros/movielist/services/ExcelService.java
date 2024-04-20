package edu.yaros.movielist.services;

import edu.yaros.movielist.models.Movie;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    public byte[] createExcelFile(List<Movie> movieList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Movies-to-watch");
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 18000);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Title", "Director", "Release Date", "URL"};
        CellStyle headerCellStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerCellStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for (Movie movie : movieList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(movie.getId());
            row.createCell(1).setCellValue(movie.getTitle());
            row.createCell(2).setCellValue(movie.getDirector());
            row.createCell(3).setCellValue(movie.getReleaseYear());
            row.createCell(4).setCellValue(movie.getUrl());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}