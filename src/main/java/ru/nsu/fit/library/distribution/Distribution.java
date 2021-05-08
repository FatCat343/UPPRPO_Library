package ru.nsu.fit.library.distribution;

import ru.nsu.fit.library.book.Book;
import ru.nsu.fit.library.reader.Reader;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "distribution")
public class Distribution {
    @Id
    @SequenceGenerator(name = "distribution_generator", sequenceName = "distribution_seq", initialValue = 60)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "distribution_generator")
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "date_give")
    private LocalDate dateGive;

    @Column(name = "date_return")
    private LocalDate dateReturn;

    public Distribution() {
    }

    public Distribution(Integer id, Reader reader, Book book, LocalDate dateGive, LocalDate dateReturn) {
        this.id = id;
        this.reader = reader;
        this.book = book;
        this.dateGive = dateGive;
        this.dateReturn = dateReturn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getDateGive() {
        return dateGive;
    }

    public void setDateGive(LocalDate dateGive) {
        this.dateGive = dateGive;
    }

    public LocalDate getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(LocalDate dateReturn) {
        this.dateReturn = dateReturn;
    }
}

