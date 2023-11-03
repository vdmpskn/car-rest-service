package ua.foxminded.carrest.dao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "car_type")
public class CarType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type")
    private String carBodyType;

    public CarType(final CarBodyType carBodyType) {
    }

    public CarType() {

    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getCarBodyType() {
        return carBodyType;
    }

    public void setCarBodyType(final CarBodyType carBodyType) {
        this.carBodyType = String.valueOf(carBodyType);
    }
}
