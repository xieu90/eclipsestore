package de.own.eclipsestore.util;

import org.dhatim.fastexcel.Worksheet;

import de.own.eclipsestore.service.CellCoordinate;
import de.own.eclipsestore.service.CellReferenceConverter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExcelUtil {
    public void writeToCell(Worksheet worksheet, String cell, Object value) {
        CellCoordinate cellCoordinate = CellReferenceConverter.toCellCoordinate(cell);
        worksheet.value(cellCoordinate.getRowNum(), cellCoordinate.getColNum(), value.toString());
    }
}
