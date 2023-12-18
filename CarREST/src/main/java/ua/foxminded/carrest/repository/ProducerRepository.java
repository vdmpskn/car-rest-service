package ua.foxminded.carrest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.carrest.dao.model.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
 Optional<Producer> findByProducerNameAndModelName(String producerName, String modelName);
}
