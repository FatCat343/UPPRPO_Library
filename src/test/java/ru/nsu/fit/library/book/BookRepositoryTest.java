package ru.nsu.fit.library.book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findNotGiven() {
        List<Book> notGivenBooks = bookRepository.findNotGiven();
        StringBuilder str = new StringBuilder();
        for (Book next : notGivenBooks) {
            str.append(next.toString()).append(" ");
        }

        boolean val1 = str.toString().contains("A Game of Thrones");
        boolean val2 = str.toString().contains("The Lord of the Rings");
        boolean val3 = str.toString().contains("A Storm of Swords");
        boolean val4 = str.toString().contains("The Hobbit");
        boolean val5 = str.toString().contains("Dune");
        boolean val6 = str.toString().contains("Anna Karenina");
        boolean val7 = str.toString().contains("War and Peace");
        boolean val8 = str.toString().contains("Hyperion");

        assertThat(val5).isTrue();
        assertThat(val6).isTrue();
        assertThat(val7).isTrue();
        assertThat(val8).isTrue();
        assertThat(val1).isFalse();
        assertThat(val2).isFalse();
        assertThat(val3).isFalse();
        assertThat(val4).isFalse();

    }

    @ParameterizedTest
    @CsvSource({"The, 7", "a, 25", "Java,0", "The Hobbit, 1"})
    @DisplayName("test search()")
    void testSearch(String input, Integer expected){
        List<Book> result = bookRepository.search(input);
        Assertions.assertEquals(expected, result.size());

    }
}
