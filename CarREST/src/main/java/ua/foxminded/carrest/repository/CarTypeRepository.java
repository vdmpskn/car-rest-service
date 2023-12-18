package ua.foxminded.carrest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.carrest.dao.model.CarType;

@Repository
public interface CarTypeRepository extends JpaRepository<CarType, Long> {
    Optional<CarType> findByCarBodyType(String name);
}
