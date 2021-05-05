package ru.nsu.fit.library.Book;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @SequenceGenerator(name = "book_generator", sequenceName = "book_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    private Integer book_id;

    private String title;

    private Integer author_id;

    public Book() {
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    @Override
    public String toString() {
        return title;
    }
}
