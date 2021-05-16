package ru.nsu.fit.library.library;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "library")
public class Library implements Serializable {
    @Id
    @SequenceGenerator(name = "library_generator", sequenceName = "library_sequence", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "library_generator")
    @Column(name = "library_id")
    private Integer id;

    @Column(name = "address")
    private String address;

    public Library(Library object) {
        if (object == null) new Library();
        else {
            this.id = object.getId();
            this.address = object.getAddress();
        }
    }

    public Library() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address;
    }

}