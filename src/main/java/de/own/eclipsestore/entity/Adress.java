package de.own.eclipsestore.entity;

import lombok.Data;

@Data
public class Adress {
    private String street;
    private int number;
    private String country;
    private String city;
}
