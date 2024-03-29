package ru.nsu.fit.library.distribution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.nsu.fit.library.book.Book;
import ru.nsu.fit.library.reader.Reader;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DistributionServiceTest {
    @Autowired
    private DistributionService service;

    @MockBean
    private DistributionRepository repository;

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        Reader r1 = new Reader(1, "Alex", "Smith");
        Book b1 = new Book();
        Distribution d1 = new Distribution(1, r1, b1, null,  LocalDate.parse("2016-03-16"), LocalDate.parse("2016-03-16"));
        Distribution d2 = new Distribution(1, r1, b1, null,  LocalDate.parse("2016-03-16"), LocalDate.parse("2016-03-16"));
        doReturn(Arrays.asList(d1, d2)).when(repository).findAll();

        List<Distribution> result = service.findAll();

        Assertions.assertEquals(2, result.size(), "findAll() should return 2 objects");
    }

    @Test
    @DisplayName("Test save")
    void testSave() {
        Reader r1 = new Reader(1, "Alex", "Smith");
        Book b1 = new Book();
        Distribution d1 = new Distribution(1, r1, b1, null,  LocalDate.parse("2016-03-16"), LocalDate.parse("2016-03-16"));
        doReturn(d1).when(repository).save(any());

        Distribution result = service.save(d1);

        Assertions.assertNotNull(result, "The saved distribution should not be null");
        Assertions.assertEquals(result, d1);
    }

    @Test
    @DisplayName("Test delete")
    void testDelete() {
        Reader r1 = new Reader(1, "Alex", "Smith");
        Book b1 = new Book();
        Distribution d1 = new Distribution(1, r1, b1, null, LocalDate.parse("2016-03-16"), LocalDate.parse("2016-03-16"));
        doNothing().when(repository).delete(d1);

        service.delete(d1);
        verify(repository, times(1)).delete(d1);
    }

    @Test
    @DisplayName("Test findDistributionFetch")
    void testFindDistributionFetch(){
        Reader r1 = new Reader(1, "Alex", "Smith");
        Book b1 = new Book();
        Distribution d1 = new Distribution(1, r1, b1, null,  LocalDate.parse("2016-03-16"), LocalDate.parse("2016-03-16"));
        doReturn(d1).when(repository).findDistributionByIdFetch(1);

        Distribution result1 = service.findDistributionFetch(d1);
        Assertions.assertNotNull(result1, "The returned distribution should not be null");
        Assertions.assertEquals(result1, d1);

        Distribution result2 = service.findDistributionFetch(null);
        Assertions.assertNull(result2, "The returned distribution should be null");
    }

    @Test
    @DisplayName("Test findAllByReader(reader)")
    void testFindAllByReader() {
        Reader r1 = new Reader(1, "Alex", "Smith");
        Book b1 = new Book();
        Distribution d1 = new Distribution(1, r1, b1, null, LocalDate.parse("2016-03-16"), LocalDate.parse("2016-03-16"));
        Distribution d2 = new Distribution(1, r1, b1, null, LocalDate.parse("2016-03-16"), LocalDate.parse("2016-03-16"));
        doReturn(Arrays.asList(d1, d2)).when(repository).findAllByReader(any());

        List<Distribution> result1 = service.findAllByReader(null);
        Assertions.assertEquals(0, result1.size(), "findAllByReader(null) should return 0 objects");

        List<Distribution> result2 = service.findAllByReader(r1);
        Assertions.assertEquals(2, result2.size(), "findAllByReader(not_null_reader) should return 2 objects");
    }
}
