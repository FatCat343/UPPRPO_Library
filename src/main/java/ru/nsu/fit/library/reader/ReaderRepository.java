package ru.nsu.fit.library.reader;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends CrudRepository<Reader, Integer> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reader r " +
            "WHERE (:id <> r.id OR :id IS NULL) AND r.firstName = :fn AND r.lastName = :ln")
    boolean existsReaderByFirstNameAndLastName(@Param("id") Integer id, @Param("fn") String fn, @Param("ln") String ln);

}
