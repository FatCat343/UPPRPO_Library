package ru.nsu.fit.library.reader;

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
public class ReaderServiceTest {
    @Autowired
    private ReaderService readerService;

    @MockBean
    private ReaderRepository readerRepository;

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        Reader reader1 = new Reader(1, "Иван", "Иванов");
        Reader reader2 = new Reader(2, "Петр", "Петров");
        Reader reader3 = new Reader(3, "Петр", "Семенов");
        doReturn(Arrays.asList(reader1, reader2, reader3)).when(readerRepository).findAll();

        List<Reader> readers = readerService.findAll();

        Assertions.assertEquals(3, readers.size(), "findAll() should return 3 readers");
    }

    @Test
    @DisplayName("Test save reader")
    void testSave() {
        Reader reader = new Reader(1, "Иван", "Иванов");
        doReturn(reader).when(readerRepository).save(any());

        Reader returnedReader = readerService.save(reader);

        Assertions.assertNotNull(returnedReader, "The saved reader should not be null");
    }

    @Test
    @DisplayName("Test delete reader")
    void testDelete() {
        Reader reader = new Reader(1, "Иван", "Иванов");
        doNothing().when(readerRepository).delete(reader);

        readerService.delete(reader);
        verify(readerRepository, times(1)).delete(reader);
    }

    @Test
    @DisplayName("Test exist reader")
    void testExist() {
        Reader reader1 = new Reader(1, "Иван", "Иванов");
        Reader reader2 = new Reader(2, "Иван", "Иванов");
        Reader reader3 = new Reader(null, "Иван", "Иванов");
        Reader reader4 = new Reader(1, "Иван", "Петров");
        Reader reader5 = new Reader(null, "Иван", "Петров");

        doReturn(false).when(readerRepository).
                existsReaderByFirstNameAndLastName(reader1.getId(), reader1.getFirstName(), reader1.getLastName());
        doReturn(true).when(readerRepository).
                existsReaderByFirstNameAndLastName(reader2.getId(), reader2.getFirstName(), reader2.getLastName());
        doReturn(true).when(readerRepository).
                existsReaderByFirstNameAndLastName(reader3.getId(), reader3.getFirstName(), reader3.getLastName());
        doReturn(false).when(readerRepository).
                existsReaderByFirstNameAndLastName(reader4.getId(), reader4.getFirstName(), reader4.getLastName());
        doReturn(false).when(readerRepository).
                existsReaderByFirstNameAndLastName(reader5.getId(), reader5.getFirstName(), reader5.getLastName());

        Assertions.assertFalse(readerService.exist(reader1));
        Assertions.assertTrue(readerService.exist(reader2));
        Assertions.assertTrue(readerService.exist(reader3));
        Assertions.assertFalse(readerService.exist(reader4));
        Assertions.assertFalse(readerService.exist(reader5));
    }
}
