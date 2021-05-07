package ru.nsu.fit.library.reader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ReaderRepositoryTest {
    @Autowired
    private ReaderRepository repository;

    @Test
    void existsReaderByFirstNameAndLastName() {
        Reader reader1 = new Reader(1, "Иван", "Иванов");
        Reader reader2 = new Reader(5, "Иван", "Иванов");
        Reader reader3 = new Reader(null, "Иван", "Иванов");
        Reader reader4 = new Reader(1, "Иван", "Петров");
        Reader reader5 = new Reader(null, "Иван", "Петров");

        //repository.save(reader1);

        boolean expected1 = repository.
                existsReaderByFirstNameAndLastName(reader1.getId(), reader1.getFirstName(), reader1.getLastName());
        boolean expected2 = repository.
                existsReaderByFirstNameAndLastName(reader2.getId(), reader2.getFirstName(), reader2.getLastName());
        boolean expected3 = repository.
                existsReaderByFirstNameAndLastName(reader3.getId(), reader3.getFirstName(), reader3.getLastName());
        boolean expected4 = repository.
                existsReaderByFirstNameAndLastName(reader4.getId(), reader4.getFirstName(), reader4.getLastName());
        boolean expected5 = repository.
                existsReaderByFirstNameAndLastName(reader5.getId(), reader5.getFirstName(), reader5.getLastName());

        assertThat(expected1).isFalse();
        assertThat(expected2).isTrue();
        assertThat(expected3).isTrue();
        assertThat(expected4).isFalse();
        assertThat(expected5).isFalse();
    }
}