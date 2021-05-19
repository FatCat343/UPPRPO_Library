package ru.nsu.fit.library.bookPosition;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookPositionRepository extends CrudRepository<BookPosition, Integer> {

    @Query(value = "SELECT p FROM BookPosition p JOIN FETCH p.storage st WHERE p.id > 0")
    List<BookPosition> findAllFetch();

}
