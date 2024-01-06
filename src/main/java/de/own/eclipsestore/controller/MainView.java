package de.own.eclipsestore.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import de.own.eclipsestore.entity.Book;
import de.own.eclipsestore.service.FakerService;
import de.own.eclipsestore.service.MyService;

@Route("")
public class MainView extends VerticalLayout {

    private MyService myService;
    private FakerService fakerService;

    public MainView(MyService myService, FakerService fakerService) {
        this.myService = myService;
        this.fakerService = fakerService;
        Button generateBook = new Button("generateBook");
        generateBook.addClickListener(click -> {
            Instant start = Instant.now();
            for (int i = 0; i < 500; i++) {
                List<Book> books = new ArrayList<>();
                for (int j = 0; j < 1000; j++) {
                    Book b = fakerService.createBook();
                    books.add(b);
                }
                myService.createBooks(books);
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            Notification.show(
                    "1000000 books should be generated. " + "Time taken: " + timeElapsed.toMillis() + " milliseconds");
            System.out.println(
                    "1000000 books should be generated" + "Time taken: " + timeElapsed.toMillis() + " milliseconds");
        });

        Button generateAuthor = new Button("generateAuthor");
        generateAuthor.addClickListener(click -> {
            for (int i = 0; i < 800000; i++) {
                myService.createAuthor(fakerService.createAuthor());
            }
            Notification.show("100 authors should be generated");
        });

        Button generateAdress = new Button("generateAdress");
        generateAdress.addClickListener(click -> {
            Instant start = Instant.now();
            for (int i = 0; i < 100000; i++) {
                myService.createAdress(fakerService.createAdress());
            }
            Notification.show("100 adress should be generated");
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            Notification.show(
                    "100000 adress should be generated. " + "Time taken: " + timeElapsed.toMillis() + " milliseconds");
            System.out.println(
                    "100000 adress should be generated" + "Time taken: " + timeElapsed.toMillis() + " milliseconds");
        });

        Button countAdress = new Button("countAdress");
        countAdress.addClickListener(click -> {
            long count = myService.data().getAdressRoot().stream().count();
            Notification.show(count + "adress found");
        });
        Button countBook = new Button("countBook");
        countBook.addClickListener(click -> {
            long count = myService.data().getBookRoot().stream().count();
            Notification.show(count + "books found");
        });
        Button countAuthor = new Button("countAuthor");
        countAuthor.addClickListener(click -> {
            long count = myService.data().getAuthorRoot().stream().count();
            Notification.show(count + "authors found");
        });

        add(new HorizontalLayout(generateAdress, generateAuthor, generateBook),
                new HorizontalLayout(countAdress, countAuthor, countBook));

    }
}
