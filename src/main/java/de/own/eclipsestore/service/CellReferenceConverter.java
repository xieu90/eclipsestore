package de.own.eclipsestore.service;

import java.util.regex.Pattern;

public class CellReferenceConverter {

    public static String fromCellCoordinate(int rowNum, int colNum) {
        if (rowNum < 0 || rowNum > 255) {
            throw new IllegalArgumentException("Invalid row number: " + rowNum);
        }

        if (colNum < 1 || colNum > 26) {
            throw new IllegalArgumentException("Invalid column number: " + colNum);
        }

        StringBuilder cellRef = new StringBuilder();

        while (colNum-- > 0) {
            char colChar = (char) (colNum % 26 + 'A');
            cellRef.append(colChar);
        }

        cellRef.append(String.format("%02d", rowNum));
        return cellRef.toString();
    }

    public static CellCoordinate toCellCoordinate(String cellRef) {
        // A1=00
        int row = 0;// 0
        int col = 0;// 0
        String twoPart = separateToTwoPart(cellRef);
        String[] alphaNumber = twoPart.split("-");
        col = convertCellNameToCoordinates(alphaNumber[0]);
        row = Integer.valueOf(alphaNumber[1]) - 1;
        return new CellCoordinate(row, col);
    }

    private static String separateToTwoPart(String cellRef) {
        String alphabet = "";
        String number = "";

        for (int i = 0; i < cellRef.length(); i++) {
            char c = cellRef.charAt(i);
            if (Pattern.matches("[A-Za-z]", String.valueOf(c))) {
                alphabet += c;
            } else if (Pattern.matches("\\d", String.valueOf(c))) {
                number += c;
            }
        }
        System.out.println("Alphabet: " + alphabet);
        System.out.println("Number: " + number);
        return alphabet.toUpperCase() + "-" + number;
    }

    private static int convertCellNameToCoordinates(String cellName) {
        int col = 0;
        int row = 0;

        for (int i = 0; i < cellName.length(); i++) {
            char ch = cellName.charAt(i);
            if (Character.isLetter(ch)) {
                col = col * 26 + (Character.toUpperCase(ch) - 'A' + 1);
            } else if (Character.isDigit(ch)) {
                row = row * 10 + (ch - '0');
            }
        }

        // System.out.println("Excel Cell Name: " + cellName);
        // System.out.println("Row Number: " + row);
        // System.out.println("Column Number: " + col);
        return col - 1;
    }

}
