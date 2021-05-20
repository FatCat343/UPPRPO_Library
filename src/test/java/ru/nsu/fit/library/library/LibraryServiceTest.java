package ru.nsu.fit.library.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LibraryServiceTest {

    @Autowired
    private LibraryService service;

    @MockBean
    private LibraryRepository repository;

    @Test
    @DisplayName("Test findAll()")
    void findAll() {
        Library lb1 = new Library(1, "7th Avenue, 14");
        Library lb2 = new Library(2, "5th Avenue, 5");
        Library lb3 = new Library(3, "1st Avenue, 12");
        doReturn(Arrays.asList(lb1, lb2, lb3)).when(repository).findAll();
        List<Library> libraryList = service.findAll();
        Assertions.assertEquals(3, libraryList.size(), "findAll() should return 3 libraries");
    }

    @Test
    @DisplayName("Test save()")
    void save() {
        Library library = new Library(1, "7th Avenue, 14");
        doReturn(library).when(repository).save(any());
        Library saved = service.save(library);
        Assertions.assertNotNull(saved, "saved library should not be null");
    }

    @Test
    @DisplayName("Test exists()")
    void exists() {
        Library library = new Library(1, "7th Avenue, 14");
        doReturn(true).when(repository).existsLibraryByAddress(any());
        service.exist(library);
        verify(repository, times(1))
                .existsLibraryByAddress(library.getAddress());
    }

    @Test
    @DisplayName("Test delete()")
    void delete() {
        Library library = new Library(1, "7th Avenue, 14");
        doNothing().when(repository).delete(library);
        service.delete(library);
        verify(repository, times(1)).delete(library);
    }
}
