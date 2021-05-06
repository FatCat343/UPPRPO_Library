package ru.nsu.fit.library.book.author;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    boolean existsAuthorByFirstNameAndLastName(String firstName, String lastName);
}
