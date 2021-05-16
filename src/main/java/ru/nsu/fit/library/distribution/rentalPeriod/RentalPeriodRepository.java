package ru.nsu.fit.library.distribution.rentalPeriod;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalPeriodRepository extends CrudRepository<RentalPeriod, Integer> {

}
