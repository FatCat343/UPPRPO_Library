package ru.nsu.fit.library.Service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.library.Entity.Book;
import ru.nsu.fit.library.Repository.BookRepository;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

}
