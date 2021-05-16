package ru.nsu.fit.library.book.author;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthorServiceTest {
    @Autowired
    private AuthorService service;

    @MockBean
    private AuthorRepository repository;

    @Test
    @DisplayName("test findAll()")
    void testFindAll() {
        Author a1 = new Author(1L, "Alex", "Smith", new ArrayList<>());
        Author a2 = new Author(2L, "Stan", "Williams", new ArrayList<>());
        doReturn(Arrays.asList(a1, a2)).when(repository).findAll();

        List<Author> authors = service.findAll();

        Assertions.assertEquals(2, authors.size(), "findAll() should return 2 authors");
    }

    @Test
    @DisplayName("test save()")
    void testSave() {
        Author a1 = new Author(1L, "Alex", "Smith", new ArrayList<>());
        doReturn(a1).when(repository).save(any());

        Author returned = service.save(a1);
        Assertions.assertNotNull(returned, "The saved author should not be null");
    }

    @Test
    @DisplayName("test delete()")
    void testDelete(){
        Author a1 = new Author(1L, "Alex", "Smith", new ArrayList<>());
        doNothing().when(repository).delete(a1);

        service.delete(a1);
        verify(repository, times(1)).delete(a1);
    }

    @Test
    @DisplayName("test exist()")
    void testExist() {
        Author a1 = new Author(1L, "Alex", "Smith", new ArrayList<>());
        doReturn(true).when(repository).existsAuthorByFirstNameAndLastName(any(), any());

        service.exist(a1);
        verify(repository, times(1))
                .existsAuthorByFirstNameAndLastName(a1.getFirstName(), a1.getLastName());
    }
}
