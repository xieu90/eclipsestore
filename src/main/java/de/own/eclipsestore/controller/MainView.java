package de.own.eclipsestore.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import de.own.eclipsestore.entity.Author;
import de.own.eclipsestore.entity.Book;
import de.own.eclipsestore.entity.Relation;
import de.own.eclipsestore.service.ExcelService;
import de.own.eclipsestore.service.FakerService;
import de.own.eclipsestore.service.MyService;

@Route("")
public class MainView extends VerticalLayout {

    private MyService myService;
    private FakerService fakerService;
    private ExcelService excelService;

    public MainView(MyService myService, FakerService fakerService, ExcelService excelService) {
        this.myService = myService;
        this.fakerService = fakerService;
        this.excelService = excelService;
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
            Instant start = Instant.now();
            for (int i = 0; i < 800; i++) {
                List<Author> authors = new ArrayList<>();
                for (int j = 0; j < 1000; j++) {
                    Author b = fakerService.createAuthor();
                    authors.add(b);
                }
                myService.createAuthors(authors);
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            Notification.show(
                    "800000 authors should be generated. " + "Time taken: " + timeElapsed.toMillis() + " milliseconds");
            System.out.println(
                    "800000 authors should be generated" + "Time taken: " + timeElapsed.toMillis() + " milliseconds");
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

        Button countRelation = new Button("countRelation", e -> {
            long count = myService.data().getRelationRoot().stream().count();
            Notification.show(count + "relations found");
        });

        Button generateRelationAuthorBook = new Button("generateRelationAuthorBook", c -> {
            long countA = myService.data().getAuthorRoot().stream().count();
            long countB = myService.data().getBookRoot().stream().count();

            if (countA > 0 && countB > 0) {
                Random r = new Random();
                int low = 0;
                Instant start = Instant.now();
                for (int i = 0; i < 100; i++) {
                    List<Relation> relations = new ArrayList<>();
                    for (int j = 0; j < 1000; j++) {
                        Relation relation = new Relation();
                        relation.setObj1(myService.data().getAuthorRoot().get(r.nextInt((int) (countA - low)) + low));
                        relation.setObj2(myService.data().getBookRoot().get(r.nextInt((int) (countB - low)) + low));
                        relations.add(relation);
                    }
                    myService.createRelations(relations);
                }
                Instant end = Instant.now();
                Duration timeElapsed = Duration.between(start, end);
                Notification.show(
                        "100000 relations should be generated. " + "Time taken: " + timeElapsed.toMillis()
                                + " milliseconds");
                System.out.println(
                        "100000 relations should be generated" + "Time taken: " + timeElapsed.toMillis()
                                + " milliseconds");
            } else {
                Notification.show("Authors or Books are empty, relation cannot be generated");
            }
        });
        Button deleteAdress = new Button("deleteAdress", e -> {
            myService.deleteAllAdress();
        });
        Button deleteAuthor = new Button("deleteAuthor", e -> {
            myService.deleteAllAuthor();
        });
        Button deleteBook = new Button("deleteBook", e -> {
            myService.deleteAllBook();
        });
        Button deleteRelation = new Button("deleteRelation", e -> {
            myService.deleteAllRelation();
        });

        Button countBooksHavingMultipleAuthor = new Button("countBooksHavingMultipleAuthor", e -> {
            Notification.show(
                    "Books having multiple Author by relation: " + myService.getBooksHavingMultipleAuthor().size());
        });
        Button writeExcel = new Button("writeExcel", c -> {
            excelService.writeToExcel();
        });
        add(new HorizontalLayout(generateAdress, generateAuthor, generateBook, generateRelationAuthorBook),
                new HorizontalLayout(countAdress, countAuthor, countBook, countRelation,
                        countBooksHavingMultipleAuthor),
                new HorizontalLayout(deleteAdress, deleteAuthor, deleteBook, deleteRelation),
                new HorizontalLayout(writeExcel));

    }
}
