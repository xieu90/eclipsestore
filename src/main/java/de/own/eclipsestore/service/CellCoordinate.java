package de.own.eclipsestore.service;

import lombok.Value;

@Value
public class CellCoordinate {
    private int rowNum;
    private int colNum;

    public CellCoordinate(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

}
