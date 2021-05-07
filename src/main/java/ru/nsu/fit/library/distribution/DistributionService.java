package ru.nsu.fit.library.distribution;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributionService {
    final DistributionRepository distributionRepository;

    public DistributionService(DistributionRepository distributionRepository) {
        this.distributionRepository = distributionRepository;
    }

    public List<Distribution> findAll(){
        return (List<Distribution>) distributionRepository.findAll();
    }

    public Distribution save(Distribution distribution){
        return distributionRepository.save(distribution);
    }

    public void delete(Distribution distribution){
        distributionRepository.delete(distribution);
    }

    public Distribution findDistributionFetch(Distribution distribution) {
        if (distribution == null) {
            return null;
        }
        else {
            return distributionRepository.findDistributionByIdFetch(distribution.getId());
        }
    }
}
