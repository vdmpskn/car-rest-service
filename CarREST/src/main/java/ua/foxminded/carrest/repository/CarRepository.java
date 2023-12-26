package ua.foxminded.carrest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import ua.foxminded.carrest.dao.model.Car;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByProducer_ProducerName(String producerName, Pageable pageable);

    Page<Car> findByProducer_ModelName(String modelName, Pageable pageable);

    Page<Car> findByYearBetween(Integer minYear, Integer maxYear, Pageable pageable);

    Optional<Car> findCarByYear(int carYear);

    List<Car> findCarByProducer_ProducerNameAndProducer_ModelNameAndYear(String producerName, String modelName, int year);

}
