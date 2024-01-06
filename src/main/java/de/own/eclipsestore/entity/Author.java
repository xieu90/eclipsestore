package de.own.eclipsestore.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Author {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
}
