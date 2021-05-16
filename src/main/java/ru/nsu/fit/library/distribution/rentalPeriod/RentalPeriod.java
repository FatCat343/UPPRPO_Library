package ru.nsu.fit.library.distribution.rentalPeriod;

import javax.persistence.*;

@Entity
@Table(name = "rental_periods")
public class RentalPeriod {
    @Id
    @SequenceGenerator(name = "rental_period_generator", sequenceName = "rental_period_seq", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_period_generator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "days")
    private Integer days;

    public RentalPeriod() {
    }

    public RentalPeriod(Integer id, Integer days) {
        this.id = id;
        this.days = days;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return days.toString() + " days";
    }
}
