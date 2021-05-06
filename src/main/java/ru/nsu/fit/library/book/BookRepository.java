package ru.nsu.fit.library.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.library.book.author.Author;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsBookByTitleAndAuthor(String title, Author author);
}
