package ru.nsu.fit.library.bookPosition;


import ru.nsu.fit.library.storage.Storage;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bookposition")
public class BookPosition implements Serializable {
    @Id
    @SequenceGenerator(name = "bookposition_generator", sequenceName = "bookposition_sequence", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookposition_generator")
    @Column(name = "position_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @Column(name = "rack_number")
    private Integer rackNumber;

    @Column(name = "shelf_number")
    private Integer shelfNumber;

    public BookPosition(BookPosition object) {
        if (object == null) new BookPosition();
        else {
            this.id = object.getId();
            this.rackNumber = object.getRackNumber();
            this.shelfNumber = object.getShelfNumber();
            this.storage = object.getStorage();
        }
    }

    public BookPosition(Integer id, Storage storage, Integer rackNumber, Integer shelfNumber) {
        this.id = id;
        this.storage = storage;
        this.rackNumber = rackNumber;
        this.shelfNumber = shelfNumber;
    }

    public BookPosition() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Integer getRackNumber() {
        return rackNumber;
    }

    public void setRackNumber(Integer rackNumber) {
        this.rackNumber = rackNumber;
    }

    public Integer getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(Integer shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    @Override
    public String toString() {
        return "shelf: " + shelfNumber + ", rack: " + rackNumber + ", " + storage.toString();
    }

}