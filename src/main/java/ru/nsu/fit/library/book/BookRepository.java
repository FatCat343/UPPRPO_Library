package ru.nsu.fit.library.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.library.book.author.Author;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsBookByTitleAndAuthor(String title, Author author);

    @Query("select b from Book b " +
            "where lower(b.title) like lower(concat('%', :searchTerm, '%'))")
    List<Book> search(@Param("searchTerm") String searchTerm);

    @Query(value = """
            SELECT *\s
            FROM books b
            WHERE b.id NOT IN (
            \tSELECT bo.id\s
            \tFROM books bo JOIN distribution di ON bo.id = di.book_id
            \tWHERE di.date_return IS NULL
            )""", nativeQuery = true)
    List<Book> findNotGiven();

}
