package de.own.eclipsestore.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.stereotype.Service;

import de.own.eclipsestore.entity.Author;
import de.own.eclipsestore.entity.Book;
import de.own.eclipsestore.util.ExcelUtil;

@Service
public class ExcelService {

    private MyService myService;

    public ExcelService(MyService myService) {
        this.myService = myService;
    }

    public void writeToExcel() {
        try (OutputStream outputStream = new FileOutputStream("fileName.xlsx")) {
            Workbook workbook = new Workbook(outputStream, "DemoExcel", "1.0");
            Instant start = Instant.now();
            writeAuthorSheet(workbook);
            writeBookSheet(workbook);
            writeRelationSheet(workbook);
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.println(
                    "Excel only content writing:" + "Time taken for only saving: "
                            + timeElapsed.toMillis() + " milliseconds");
            workbook.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeAuthorSheet(Workbook workbook) {
        Worksheet author = workbook.newWorksheet("Author");
        // worksheet.value(0, 0, "value");
        ExcelUtil.writeToCell(author, "A1", "Firstname");
        ExcelUtil.writeToCell(author, "B1", "Lastname");
        AtomicInteger authorIndex = new AtomicInteger(2);
        myService.data().getAuthorRoot().stream().limit(1000).forEach(c -> {
            ExcelUtil.writeToCell(author, "A" + authorIndex.get(), c.getFirstName());
            ExcelUtil.writeToCell(author, "B" + authorIndex.getAndIncrement(), c.getLastName());
        });
    }

    private void writeBookSheet(Workbook workbook) {
        Worksheet book = workbook.newWorksheet("Book");
        ExcelUtil.writeToCell(book, "A1", "Name");
        AtomicInteger bookIndex = new AtomicInteger(2);
        myService.data().getBookRoot().stream().limit(1000).forEach(c -> {
            ExcelUtil.writeToCell(book, "A" + bookIndex.get(), c.getName());
            ExcelUtil.writeToCell(book, "B" + bookIndex.getAndIncrement(), c.getPublishDate());
        });
    }

    private void writeRelationSheet(Workbook workbook) {
        Worksheet relation = workbook.newWorksheet("Relation");
        ExcelUtil.writeToCell(relation, "A1", "Author Firstname");
        ExcelUtil.writeToCell(relation, "B1", "Author Lastname");
        ExcelUtil.writeToCell(relation, "D1", "Book Name");
        ExcelUtil.writeToCell(relation, "D2", "Publish date");

        AtomicInteger relationIndex = new AtomicInteger(2);
        myService.data().getRelationRoot().stream().forEach(c -> {
            ExcelUtil.writeToCell(relation, "A" + relationIndex.get(), ((Author) c.getObj1()).getFirstName());
            ExcelUtil.writeToCell(relation, "B" + relationIndex.get(), ((Author) c.getObj1()).getLastName());
            ExcelUtil.writeToCell(relation, "C" + relationIndex.get(), ((Book) c.getObj2()).getName());
            ExcelUtil.writeToCell(relation, "D" + relationIndex.getAndIncrement(),
                    ((Book) c.getObj2()).getPublishDate());
        });
    }

    public void readExcel() {

    }
}
