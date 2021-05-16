package ru.nsu.fit.library.distribution.rentalPeriod;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalPeriodService {
    final RentalPeriodRepository rentalPeriodRepository;

    public RentalPeriodService(RentalPeriodRepository rentalPeriodRepository) {
        this.rentalPeriodRepository = rentalPeriodRepository;
    }

    public List<RentalPeriod> findAll() {
        return (List<RentalPeriod>) rentalPeriodRepository.findAll();
    }

    public RentalPeriod save(RentalPeriod rentalPeriod) {
        return rentalPeriodRepository.save(rentalPeriod);
    }


}
