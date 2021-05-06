package ru.nsu.fit.library.reader;

import javax.persistence.*;

@Entity
@Table(name = "reader")
public class Reader {
    @Id
    @SequenceGenerator(name = "reader_generator", sequenceName = "reader_seq", initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reader_generator")
    @Column(name = "reader_id")
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    public Reader() {
    }

    public Reader(Reader object) {
        if (object == null) new Reader();
        else {
            this.id = object.getId();
            this.firstName = object.getFirstName();
            this.lastName = object.getLastName();
        }
    }

    public Reader(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
