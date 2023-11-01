package ua.foxminded.carrest.dao.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private int year;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Producer producer;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Enumerated(EnumType.STRING)
    private Set<CarType> carType;

    public Car(final String objectId, final int year, final Producer producer) {
    }

    public Car() {

    }


    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(final Producer producer) {
        this.producer = producer;
    }

    public Set<CarType> getCarType() {
        return carType;
    }

    public void setCarType(final Set<CarType> carType) {
        this.carType = carType;
    }
}
