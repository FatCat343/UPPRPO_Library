package ru.nsu.fit.library.book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.nsu.fit.library.book.author.Author;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService service;

    @MockBean
    private BookRepository repository;

    @Test
    @DisplayName("test finding all books")
    void testFindAll() {
        Author author = new Author(1L, "Alex", "Smith", new ArrayList<>());
        Book b1 = new Book(1L, "title 1", author);
        Book b2 = new Book(2L, "Title 2", author);
        doReturn(Arrays.asList(b1, b2)).when(repository).findAll();

        List<Book> books = service.findAll();

        Assertions.assertEquals(2, books.size(), "findAll() should return 2 books");
    }

    @Test
    @DisplayName("test finding books with text in title")
    void testFindAllByTitle() {
        Author author = new Author(1L, "Alex", "Smith", new ArrayList<>());
        Book b1 = new Book(1L, "title 1", author);
        Book b2 = new Book(2L, "Title text2", author);
        Book b3 = new Book(3L, "title text 3", author);
        doReturn(Arrays.asList(b1, b2, b3)).when(repository).findAll();
        doReturn(Arrays.asList(b1, b2)).when(repository).search("text");

        List<Book> books1 = service.findAll(null);
        Assertions.assertEquals(3, books1.size(), "findAll(null) should return 3 books");

        List<Book> books2 = service.findAll("");
        Assertions.assertEquals(3, books2.size(), "findAll(empty_string) should return 3 books");

        List<Book> books3 = service.findAll("text");
        Assertions.assertEquals(2, books3.size(), "findAll(text) should return 2 books");
    }

    @Test
    @DisplayName("test fetching")
    void testFetch() {
        Author author = new Author(1L, "Alex", "Smith", new ArrayList<>());
        Book b1 = new Book(1L, "title 1", author);
        Book b2 = new Book(2L, "Title text2", author);
        Book b3 = new Book(3L, "title text 3", author);
        doReturn(new PageImpl<>(Arrays.asList(b1, b2, b3))).when(repository).findAll(PageRequest.of(1, 1));

        List<Book> books = service.fetch(1, 1);

        Assertions.assertEquals(3, books.size(), "findAll(1, 1) should return 3 books");
    }

    @Test
    @DisplayName("test save()")
    void testSave() {
        Author author = new Author(1L, "Alex", "Smith", new ArrayList<>());
        Book b1 = new Book(1L, "title 1", author);
        doReturn(b1).when(repository).save(any());

        Book returned = service.save(b1);
        Assertions.assertNotNull(returned, "The saved book should not be null");
    }

    @Test
    @DisplayName("test delete()")
    void testDelete(){
        Author author = new Author(1L, "Alex", "Smith", new ArrayList<>());
        Book b1 = new Book(1L, "title 1", author);
        doNothing().when(repository).delete(b1);

        service.delete(b1);
        verify(repository, times(1)).delete(b1);
    }

    @Test
    @DisplayName("test exist()")
    void testExist() {
        Author author = new Author(1L, "Alex", "Smith", new ArrayList<>());
        Book b1 = new Book(1L, "title 1", author);
        doReturn(true).when(repository).existsBookByTitleAndAuthor(any(), any());

        service.exist(b1);
        verify(repository, times(1)).existsBookByTitleAndAuthor(b1.getTitle(), b1.getAuthor());
    }

    @Test
    @DisplayName("test findNotGiven()")
    void testFindNotGiven() {
        doReturn(new ArrayList<>()).when(repository).findNotGiven();
        service.findNotGiven();
        verify(repository, times(1)).findNotGiven();
    }
}
