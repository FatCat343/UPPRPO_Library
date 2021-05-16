package ru.nsu.fit.library.storage;

import ru.nsu.fit.library.library.Library;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "storage")
public class Storage implements Serializable {
    @Id
    @SequenceGenerator(name = "storage_generator", sequenceName = "storage_sequence", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_generator")
    @Column(name = "storage_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "library_id")
    private Library library;

    @Column(name = "room_number")
    private Integer roomNumber;

    public Storage() {
    }

    public Storage(Storage object) {
        if (object == null) new Storage();
        else {
            this.id = object.getId();
            this.library = object.getLibrary();
            this.roomNumber = object.getRoomNumber();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return roomNumber +" room in " + library.getAddress();
    }

}