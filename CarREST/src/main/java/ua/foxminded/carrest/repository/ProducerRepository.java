package ua.foxminded.carrest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.carrest.dao.model.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

    List<Producer> findByProducerName(String producerName);

    void deleteByModelName(String modelName);
}
