package ru.nsu.fit.library.distribution.rentalPeriod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.nsu.fit.library.book.author.Author;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class RentalPeriodServiceTest {
    @Autowired
    private RentalPeriodService service;

    @MockBean
    private RentalPeriodRepository repository;

    @Test
    void findAll() {
        RentalPeriod rp1 = new RentalPeriod(1, 28);
        RentalPeriod rp2 = new RentalPeriod(2, 28);
        doReturn(Arrays.asList(rp1, rp2)).when(repository).findAll();

        List<RentalPeriod> returned = service.findAll();

        Assertions.assertEquals(2, returned.size(), "findAll() should return 2 objects");
    }

    @Test
    void save() {
        RentalPeriod rp1 = new RentalPeriod(1, 28);
        doReturn(rp1).when(repository).save(any());

        RentalPeriod returned = service.save(rp1);
        Assertions.assertNotNull(returned, "The saved rental period should not be null");
    }
}