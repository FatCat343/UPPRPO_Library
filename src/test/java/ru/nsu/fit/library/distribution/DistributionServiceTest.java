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
        Distribution d1 = new Distribution(1, r1, b1, LocalDate.parse("2016-03-16"), LocalDate.parse("2016-03-16"));
        Distribution d2 = new Distribution(1, r1, b1, LocalDate.parse("2016-03-16"), LocalDate.parse("2016-03-16"));
        doReturn(Arrays.asList(d1, d2)).when(repository).findAll();

        List<Distribution> result = service.findAll();

        Assertions.assertEquals(2, result.size(), "findAll() should return 2 objects");
    }

    @Test
    @DisplayName("Test save")
    void testSave() {
        Reader r1 = new Reader(1, "Alex", "Smith");
        Book b1 = new Book();
        Distribution d1 = new Distribution(1, r1, b1, LocalDate.parse("2016-03-16"), LocalDate.parse("2016-03-16"));
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
        Distribution d1 = new Distribution(1, r1, b1, LocalDate.parse("2016-03-16"), LocalDate.parse("2016-03-16"));
        doNothing().when(repository).delete(d1);

        service.delete(d1);
        verify(repository, times(1)).delete(d1);
    }
}
