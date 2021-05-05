package ru.nsu.fit.library.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.library.Entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {


}
