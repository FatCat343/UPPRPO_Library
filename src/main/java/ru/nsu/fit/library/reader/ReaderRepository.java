package ru.nsu.fit.library.reader;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends CrudRepository<Reader, Integer> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reader r " +
            "WHERE (:id <> r.id OR :id IS NULL) AND r.firstName = :fn AND r.lastName = :ln")
    boolean existsReaderByFirstNameAndLastName(@Param("id") Integer id, @Param("fn") String fn, @Param("ln") String ln);

    @Query(value = """
            select concat(r.firstname, ' ', r.lastname) as name, b.title
            from readers r
            join distribution d on r.id = d.reader_id
            join books b on b.id = d.book_id
            join rental_periods rp on rp.id = d.rental_period_id
            where d.date_return is null and d.date_give + rp.days < now()""", nativeQuery = true)
    List<Object[]> findReadersExpired();

}
