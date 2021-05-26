package ru.nsu.fit.library.book.author;

import ru.nsu.fit.library.book.Book;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author implements Serializable {
    @Id
    @SequenceGenerator(name = "author_generator", sequenceName = "author_sequence", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

//    Пока закомментировал, т.к. конфликтует с "ON DELETE CASCADE" в скриптах создания таблиц бд
//    @OneToMany(cascade = CascadeType.REMOVE)
//    private List<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Author() {}

    public Author(Long id, String firstName, String lastName, List<Book> books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
//        this.books = books;
    }

    public Author(Author object) {
        if (object == null) {
            new Author();
        } else {
            this.id = object.getId();
            this.firstName = object.getFirstName();
            this.lastName = object.getLastName();
        }
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Author)) {
            return false;
        }
        Author author = (Author) obj;
        return this.id.equals(author.id) &&
                this.firstName.equals(author.firstName) &&
                this.lastName.equals(author.lastName);
    }
}
