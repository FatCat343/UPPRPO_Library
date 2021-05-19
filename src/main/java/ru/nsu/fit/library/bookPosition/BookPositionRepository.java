package ru.nsu.fit.library.bookPosition;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPositionRepository extends CrudRepository<BookPosition, Integer> {

}
