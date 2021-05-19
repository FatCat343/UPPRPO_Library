package ru.nsu.fit.library.book;

import ru.nsu.fit.library.book.author.Author;
import ru.nsu.fit.library.bookPosition.BookPosition;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @SequenceGenerator(name = "book_generator", sequenceName = "book_sequence", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    private Long id;

    private String title;

    @ManyToOne(targetEntity = Author.class)
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id")
    private BookPosition bookPosition;

    public Book() {}

    public Book(Long id, String title, Author author, BookPosition bookPosition) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.bookPosition = bookPosition;
    }

    public Book(Book object) {
        if (object == null) {
            new Book();
        } else {
            this.id = object.getId();
            this.title = object.title;
            this.author = object.author;
            this.bookPosition = object.bookPosition;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BookPosition getBookPosition() {
        return bookPosition;
    }

    public void setBookPosition(BookPosition bookPosition) {
        this.bookPosition = bookPosition;
    }

    public String getBookLocation() {
        String rack = bookPosition.getRackNumber().toString();
        String shelf = bookPosition.getShelfNumber().toString();
        String roomNumber = bookPosition.getStorage().getRoomNumber().toString();
        String library = bookPosition.getStorage().getLibrary().getAddress();

        return "Library: " + library + ",  Room: " + roomNumber + ",  Shelf: " + shelf + ",  Rack: " + rack;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Book)) {
            return false;
        }
        Book book = (Book) obj;
        return this.id.equals(book.id) &&
                this.title.equals(book.title) &&
                this.author.equals(book.author);
    }
}
