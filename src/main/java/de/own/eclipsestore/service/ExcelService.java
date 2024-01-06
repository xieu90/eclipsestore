package de.own.eclipsestore.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {

    public void writeToExcel() {
        try (OutputStream outputStream = new FileOutputStream("fileName.xlsx")) {
            Workbook workbook = new Workbook(outputStream, "DemoExcel", "1.0");
            Worksheet author = workbook.newWorksheet("Author");
            // worksheet.value(0, 0, "value");
            writeToCell(author, "A1", "Firstname");
            writeToCell(author, "B1", "Lastname");

            Worksheet book = workbook.newWorksheet("Book");
            writeToCell(book, "A1", "Name");
            workbook.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeToCell(Worksheet worksheet, String cell, Object value) {
        CellCoordinate cellCoordinate = CellReferenceConverter.toCellCoordinate(cell);
        worksheet.value(cellCoordinate.getRowNum(), cellCoordinate.getColNum(), (String) value);
    }

    public void readExcel() {

    }
}
