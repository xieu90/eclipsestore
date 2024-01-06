package de.own.eclipsestore.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Book {
    private String name;
    private LocalDate publishDate;
}
