package ru.nsu.fit.library.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.library.book.author.Author;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsBookByTitleAndAuthor(String title, Author author);

    @Query(value = """
            SELECT *\s
            FROM library_schema.books b
            WHERE b.id NOT IN (
            \tSELECT bo.id\s
            \tFROM library_schema.books bo JOIN library_schema.distribution di ON bo.id = di.book_id
            \tWHERE di.date_return IS NULL
            )""", nativeQuery = true)
    List<Book> findNotGiven();
}
