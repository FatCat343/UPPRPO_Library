package ru.nsu.fit.library.distribution;

import org.springframework.stereotype.Service;
import ru.nsu.fit.library.reader.Reader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DistributionService {
    final DistributionRepository distributionRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

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

    public List<Distribution> findAllByGivenDate(LocalDate start, LocalDate finish){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Distribution> query = criteriaBuilder.createQuery(Distribution.class);
        Root<Distribution> root = query.from(Distribution.class);
        root.fetch("reader", JoinType.INNER);
        root.fetch("book", JoinType.INNER);
        query.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (start != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("dateGive"), start));
        }
        if (finish != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("dateGive"), finish));
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(criteriaBuilder.asc(root.get("id")));
        List<Distribution> resEdition = entityManager.createQuery(query).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return resEdition;
    }

    public List<Distribution> findAllByReader(Reader reader) {
        if (reader == null) return new ArrayList<>();
        else return distributionRepository.findAllByReader(reader);
    }
}
