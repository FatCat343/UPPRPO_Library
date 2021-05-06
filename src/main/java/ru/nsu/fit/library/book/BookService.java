package ru.nsu.fit.library.book;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.nsu.fit.library.book.author.Author;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    public List<Book> fetch(int page, int limit) {
        PageRequest pr = PageRequest.of(page, limit);
        return bookRepository.findAll(pr).getContent();
    }

    public int getCount(int limit) {
        PageRequest pr = PageRequest.of(0, limit);
        return bookRepository.findAll(pr).getTotalPages();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public boolean exist(Book book) {
        return bookRepository.existsBookByTitleAndAuthor(book.getTitle(), book.getAuthor());
    }

}
