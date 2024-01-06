package de.own.eclipsestore.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MyEclipseStoreRoot {
    private List<Adress> adressRoot = new ArrayList<>();
    private List<Book> bookRoot = new ArrayList<>();
    private List<Author> authorRoot = new ArrayList<>();
    private List<Relation> relationRoot = new ArrayList<>();
}
