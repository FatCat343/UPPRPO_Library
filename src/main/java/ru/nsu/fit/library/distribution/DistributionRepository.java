package ru.nsu.fit.library.distribution;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.library.reader.Reader;

import java.util.List;

@Repository
public interface DistributionRepository extends CrudRepository<Distribution, Integer> {
    @Query(value = "SELECT d FROM Distribution d JOIN FETCH d.book b JOIN FETCH d.rentalPeriod rp JOIN FETCH d.reader r WHERE d.id = :id")
    Distribution findDistributionByIdFetch(@Param("id") Integer id);

    List<Distribution> findAllByReader(Reader reader);
}
