package ru.nsu.fit.library.bookPosition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookPositionServiceTest {

    @Autowired
    private BookPositionService service;

    @MockBean
    private BookPositionRepository repository;

    @Test
    void findAll() {
        BookPosition bp1 = new BookPosition(1, null, 1, 1);
        BookPosition bp2 = new BookPosition(2, null, 2, 2);
        BookPosition bp3 = new BookPosition(3, null, 3, 3);
        doReturn(Arrays.asList(bp1, bp2, bp3)).when(repository).findAll();
        List<BookPosition> bookPositionList = service.findAll();
        Assertions.assertEquals(3, bookPositionList.size(), "findAll() should return 3 BookPositions");
    }

    @Test
    void save() {
        BookPosition bookPosition = new BookPosition(1, null, 1, 1);
        doReturn(bookPosition).when(repository).save(any());
        BookPosition saved = service.save(bookPosition);
        Assertions.assertNotNull(saved, "saved BookPosition should not be null");
    }

    @Test
    void exists() {
        BookPosition bookPosition = new BookPosition(1, null, 1, 1);
        doReturn(true).when(repository).existsBookPositionByStorageAndRackNumberAndShelfNumber(any(), any(), any());
        service.exists(bookPosition);
        verify(repository, times(1))
                .existsBookPositionByStorageAndRackNumberAndShelfNumber(null, bookPosition.getRackNumber(), bookPosition.getShelfNumber());
    }

    @Test
    void delete() {
        BookPosition bookPosition = new BookPosition(1, null, 1, 1);
        doNothing().when(repository).delete(bookPosition);
        service.delete(bookPosition);
        verify(repository, times(1)).delete(bookPosition);
    }

    @Test
    void findAllFetch() {
        BookPosition bp1 = new BookPosition(1, null, 1, 1);
        BookPosition bp2 = new BookPosition(2, null, 2, 2);
        BookPosition bp3 = new BookPosition(3, null, 3, 3);
        doReturn(Arrays.asList(bp1, bp2, bp3)).when(repository).findAll();
        List<BookPosition> bookPositionList = service.findAll();
        Assertions.assertEquals(3, bookPositionList.size(), "findAllFetch() should return 3 BookPositions");
    }

}
