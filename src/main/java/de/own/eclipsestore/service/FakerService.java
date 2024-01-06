package de.own.eclipsestore.service;

import java.time.ZoneId;

import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import de.own.eclipsestore.entity.Adress;
import de.own.eclipsestore.entity.Author;
import de.own.eclipsestore.entity.Book;

@Service
public class FakerService {
    public static Faker faker = new Faker();

    public Book createBook() {
        Book b = new Book();
        b.setName(faker.book().title());
        b.setPublishDate(faker.date().birthday().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        return b;
    }

    public Author createAuthor() {
        Author a = new Author();
        a.setFirstName(faker.funnyName().name());
        a.setLastName(faker.funnyName().name());
        a.setBirthday(faker.date().birthday().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        return a;
    }

    public Adress createAdress() {
        Adress a = new Adress();
        a.setCity(faker.address().city());
        a.setCountry(faker.address().country());
        a.setStreet(faker.address().streetAddress());
        a.setNumber(Integer.valueOf(faker.address().buildingNumber()));
        return a;
    }
}
