package ru.nsu.fit.library.book;

import ru.nsu.fit.library.book.author.Author;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @SequenceGenerator(name = "book_generator", sequenceName = "book_sequence", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    private Long id;

    private String title;

    @ManyToOne(targetEntity = Author.class, cascade = CascadeType.PERSIST)
    private Author author;

    public Book() {}

    public Book(Book object) {
        if (object == null) {
            new Book();
        } else {
            this.id = object.getId();
            this.title = object.title;
            this.author = object.author;
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
