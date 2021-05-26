package ru.nsu.fit.library.storage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.library.library.Library;

import java.util.List;

@Repository
public interface StorageRepository extends CrudRepository<Storage, Integer> {

    @Override
    @Query(value = "SELECT * FROM storage s WHERE s.storage_id != 0 ORDER BY s.Storage_id ASC", nativeQuery = true)
    List<Storage> findAll();

    boolean existsDistinctByLibraryAndRoomNumber(Library library, Integer roomNumber);

}
