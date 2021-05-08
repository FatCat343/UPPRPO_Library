package ru.nsu.fit.library.distribution;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class DistributionRepositoryTest {
    @Autowired
    DistributionRepository repository;

    @Test
    void findDistributionByIdFetch() {
        int id = 1;
        Distribution d = repository.findDistributionByIdFetch(id);
        assertThat(d.getId()).isEqualTo(id);
        assertThat(d.getBook().getId()).isEqualTo(id);
        assertThat(d.getReader().getId()).isEqualTo(id);
        assertThat(d.getDateGive().toString()).isEqualTo("2015-03-16");
        assertThat(d.getDateReturn().toString()).isEqualTo("2015-04-16");
    }
}
