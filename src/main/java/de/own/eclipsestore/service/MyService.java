package de.own.eclipsestore.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.store.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.stereotype.Service;

import de.own.eclipsestore.entity.Adress;
import de.own.eclipsestore.entity.Author;
import de.own.eclipsestore.entity.Book;
import de.own.eclipsestore.entity.MyEclipseStoreRoot;
import de.own.eclipsestore.entity.Relation;
import lombok.Data;

@Service
public class MyService {

    private EmbeddedStorageManager storageManager;

    public EmbeddedStorageManager storageManager() {
        /*
         * Double-checked locking to reduce the overhead of acquiring a lock
         * by testing the locking criterion.
         * The field (this.storageManager) has to be volatile.
         */
        if (this.storageManager == null) {
            synchronized (this) {
                if (this.storageManager == null) {
                    this.storageManager = this.createStorageManager();
                }
            }
        }

        return this.storageManager;
    }

    /**
     * Creates an {@link EmbeddedStorageManager} and initializes random {@link Data}
     * if empty.
     */
    private EmbeddedStorageManager createStorageManager() {
        // this.logger().info("Initializing EclipseStore StorageManager");

        // final EmbeddedStorageFoundation<?> foundation =
        // EmbeddedStorageConfiguration.Builder()
        // .setStorageDirectory("data/storage")
        // .setChannelCount(Math.max(
        // 1, // minimum one channel, if only 1 core is available
        // Integer.highestOneBit(Runtime.getRuntime().availableProcessors() - 1)))
        // .createEmbeddedStorageFoundation();

        // foundation.onConnectionFoundation(BinaryHandlersJDK8::registerJDK8TypeHandlers);
        // final EmbeddedStorageManager storageManager =
        // foundation.createEmbeddedStorageManager().start();

        // if (storageManager.root() == null) {
        // this.logger().info("No data found, initializing random data");

        // final Data data = new Data();
        // storageManager.setRoot(data);
        // storageManager.storeRoot();
        // final DataMetrics metrics = data.populate(
        // this.initialDataAmount,
        // storageManager);

        // this.logger().info("Random data generated: " + metrics.toString());
        // }

        // return storageManager;

        // //my previous default working version
        // // Application-specific root instance
        // final MyEclipseStoreRoot root = new MyEclipseStoreRoot();

        // // Initialize a storage manager ("the database") with the given directory.
        // final EmbeddedStorageManager storageManager = EmbeddedStorage.start(
        // root, // root object
        // Paths.get("myEclipseStorage") // storage directory
        // );
        // return storageManager;

        // Application-specific root instance
        final MyEclipseStoreRoot root = new MyEclipseStoreRoot();
        EmbeddedStorageManager storageManager = EmbeddedStorageConfiguration.Builder()
                .setChannelCount(4)
                .createEmbeddedStorageFoundation()
                .createEmbeddedStorageManager();
        storageManager.setRoot(root);
        storageManager.start();
        return storageManager;

    }

    /**
     * Gets the {@link Data} root object of this demo.
     * This is the entry point to all of the data used in this application,
     * basically the "database".
     *
     * @return the {@link Data} root object of this demo
     */
    public MyEclipseStoreRoot data() {
        return (MyEclipseStoreRoot) this.storageManager().root();
    }

    /**
     * Shuts down the {@link EmbeddedStorageManager} of this demo.
     */
    public synchronized void shutdown() {
        if (this.storageManager != null) {
            this.storageManager.shutdown();
            this.storageManager = null;
        }
    }

    // -------------------------------------------------
    public void createAdress(Adress item) {
        data().getAdressRoot().add(item);
        storageManager.storeRoot();// lost data after restart server
        storageManager.store(data().getAdressRoot());// data will be saved after restart server
    }

    public void createAuthor(Author item) {
        data().getAuthorRoot().add(item);
        storageManager.store(data().getAuthorRoot());// data will be saved after restart server
    }

    public void createBook(Book item) {
        data().getBookRoot().add(item);
        storageManager.storeRoot();
        storageManager.store(data().getBookRoot());// data will be saved after restart server
    }

    public void createBooks(List<Book> item) {
        Instant start = Instant.now();
        data().getBookRoot().addAll(item);
        storageManager.store(data().getBookRoot());// data will be saved after restart server
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println(
                "1000 books in chunk should be saved by eclipsestore" + "Time taken for only saving: "
                        + timeElapsed.toMillis() + " milliseconds");
    }

    public void createAuthors(List<Author> item) {
        Instant start = Instant.now();
        data().getAuthorRoot().addAll(item);
        storageManager.store(data().getAuthorRoot());// data will be saved after restart server
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println(
                "1000 authorss in chunk should be saved by eclipsestore" + "Time taken for only saving: "
                        + timeElapsed.toMillis() + " milliseconds");
    }

    public void createRelations(List<Relation> item) {
        Instant start = Instant.now();
        data().getRelationRoot().addAll(item);
        storageManager.store(data().getRelationRoot());// data will be saved after restart server
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println(
                "1000 Relations in chunk should be saved by eclipsestore" + "Time taken for only saving: "
                        + timeElapsed.toMillis() + " milliseconds");
    }

    public void createRelation(Relation item) {
        data().getRelationRoot().add(item);
        storageManager.storeRoot();
        storageManager.store(data().getRelationRoot());// data will be saved after restart server
    }

    public void deleteBook(Book item) {
        data().getBookRoot().remove(item);
        storageManager.storeRoot();
        storageManager.store(data().getBookRoot());// data will be saved after restart server
    }

    public void deleteAuthor(Author item) {
        data().getAuthorRoot().remove(item);
        storageManager.storeRoot();
        storageManager.store(data().getAuthorRoot());// data will be saved after restart server
    }

    public void deleteRelation(Relation item) {
        data().getRelationRoot().remove(item);
        storageManager.storeRoot();
        storageManager.store(data().getRelationRoot());// data will be saved after restart server
    }

    public void deleteRelations(List<Relation> item) {
        data().getRelationRoot().removeAll(item);
        storageManager.storeRoot();
        storageManager.store(data().getRelationRoot());// data will be saved after restart server
    }

    public List<Book> getBookByName(String name) {
        return data().getBookRoot().stream().filter(b -> b.getName().contains(name)).collect(Collectors.toList());
    }

    public List<Author> getAuthorByFirstName(String name) {
        return data().getAuthorRoot().stream().filter(b -> b.getFirstName().contains(name))
                .collect(Collectors.toList());
    }

    // how many book have more than one author?
    public List<Book> getBooksHavingMultipleAuthor() {
        // return data().getBookRoot().stream().filter(b -> {
        // List<Relation> currentBookRelations = data().getRelationRoot().stream()
        // .filter(r -> r.getObj1().equals(b) ||
        // r.getObj2().equals(b)).collect(Collectors.toList());
        // if (currentBookRelations.size() > 0) {
        // return true;
        // }
        // return false;
        // }).collect(Collectors.toList());

        // List<Relation> relationsList = data().getBookRoot().stream()
        // .flatMap(book -> data().getRelationRoot().stream()
        // .filter(relation -> relation.getObj1().equals(book) ||
        // relation.getObj2().equals(book)))
        // .collect(Collectors.toList());
        // List<Book> booksWithMultipleRelations = relationsList.stream()
        // .flatMap(relation -> {
        // if (relation.getObj1() instanceof Book) {
        // return Stream.of((Book) relation.getObj1());
        // } else if (relation.getObj2() instanceof Book) {
        // return Stream.of((Book) relation.getObj2());
        // } else {
        // throw new IllegalArgumentException("Relation cannot be null or contain
        // non-Book objects");
        // }
        // })
        // .distinct()
        // .collect(Collectors.toList());
        // return booksWithMultipleRelations;

        // working version, quite fast, not sure if result if correct , may be, may be not
        Map<Book, Integer> occurrenceCountMap = new HashMap<>();

        for (Relation relation : data().getRelationRoot()) {
            Object obj1 = relation.getObj1();
            Object obj2 = relation.getObj2();

            if (obj1 instanceof Book) {
                Book book = (Book) obj1;
                if (occurrenceCountMap.containsKey(obj1)) {
                    int occurrenceCount = occurrenceCountMap.get(obj1);
                    occurrenceCountMap.put(book, occurrenceCount + 1);
                } else {
                    occurrenceCountMap.put(book, 1);
                }
            }
            if (obj2 instanceof Book) {
                Book book = (Book) obj2;
                if (occurrenceCountMap.containsKey(obj2)) {
                    int occurrenceCount = occurrenceCountMap.get(obj2);
                    occurrenceCountMap.put(book, occurrenceCount + 1);
                } else {
                    occurrenceCountMap.put(book, 1);
                }
            }

        }

        List<Book> objectsWithMultipleRelations = new ArrayList<>();

        for (Book obj : occurrenceCountMap.keySet()) {
            if (occurrenceCountMap.get(obj) > 1) {
                objectsWithMultipleRelations.add(obj);
            }
        }

        return objectsWithMultipleRelations;

        // burn computer and slow....
        // List<Book> objectsWithMultipleRelations = data().getRelationRoot().stream()
        // .flatMap(relation -> Stream.of(relation.getObj1(), relation.getObj2()))
        // .filter(object -> object instanceof Book)
        // .distinct()
        // .collect(Collectors.toSet())
        // .stream()
        // .filter(book -> data().getRelationRoot().stream()
        // .filter(relation -> relation.getObj1() == book || relation.getObj2() == book)
        // .count() > 1)
        // .map(o -> (Book) o)
        // .collect(Collectors.toList());
        // return objectsWithMultipleRelations;
    }

    public void deleteAllAuthor() {
        data().getAuthorRoot().clear();
        storageManager.store(data().getAuthorRoot());
    }

    public void deleteAllBook() {
        data().getBookRoot().clear();
        storageManager.store(data().getBookRoot());
    }

    public void deleteAllAdress() {
        data().getAdressRoot().clear();
        storageManager.store(data().getAdressRoot());
    }

    public void deleteAllRelation() {
        data().getRelationRoot().clear();
        storageManager.store(data().getRelationRoot());
    }
}
