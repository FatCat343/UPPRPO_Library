package ru.nsu.fit.library.library;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends CrudRepository<Library, Integer> {

    @Override
    @Query(value = "SELECT l.* FROM library l WHERE l.library_id != 0 ORDER BY l.library_id ASC", nativeQuery = true)
    Iterable<Library> findAll();

    boolean existsLibraryByAddress(String address);

}
