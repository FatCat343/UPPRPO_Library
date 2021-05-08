package ru.nsu.fit.library.book;

import org.junit.jupiter.api.Test;
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
        String str = "";
        for (Book next : notGivenBooks) {
            str += next.toString() + " ";
        }

        boolean val1 = str.contains("A Game of Thrones");
        boolean val2 = str.contains("The Lord of the Rings");
        boolean val3 = str.contains("A Storm of Swords");
        boolean val4 = str.contains("The Hobbit");
        boolean val5 = str.contains("Dune");
        boolean val6 = str.contains("Anna Karenina");
        boolean val7 = str.contains("War and Peace");
        boolean val8 = str.contains("Hyperion");

        assertThat(val1).isFalse();
        assertThat(val2).isTrue();
        assertThat(val3).isTrue();
        assertThat(val4).isTrue();
        assertThat(val5).isFalse();
        assertThat(val6).isFalse();
        assertThat(val7).isFalse();
        assertThat(val8).isFalse();

    }
}
