package ru.nsu.fit.library.distribution;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(DistributionService.class)
public class DistributionServiceCriteriaAPITest {
    @Autowired
    private DistributionService service;

    @Test
    void findAllByDateGive() {
        List<Distribution> result = service.findAllByGivenDate(null, null);
        assertThat(result.size()).isEqualTo(24);

        result = service.findAllByGivenDate(null, LocalDate.parse("2021-04-16"));
        assertThat(result.size()).isEqualTo(24);

        result = service.findAllByGivenDate(LocalDate.parse("2010-04-16"), null);
        assertThat(result.size()).isEqualTo(24);

        result = service.findAllByGivenDate(LocalDate.parse("2010-04-16"), LocalDate.parse("2021-04-16"));
        assertThat(result.size()).isEqualTo(24);

        result = service.findAllByGivenDate(LocalDate.parse("2015-04-16"), LocalDate.parse("2016-04-16"));
        assertThat(result.size()).isEqualTo(8);
    }
}
