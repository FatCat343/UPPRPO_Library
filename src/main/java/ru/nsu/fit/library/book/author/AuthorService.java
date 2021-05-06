package ru.nsu.fit.library.book.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> findAll() {
        return (List<Author>) authorRepository.findAll();
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public void delete(Author author) {
        authorRepository.delete(author);
    }

    public boolean exist(Author author) {
        return authorRepository.existsAuthorByFirstNameAndLastName(author.getFirstName(), author.getLastName());
    }
}
