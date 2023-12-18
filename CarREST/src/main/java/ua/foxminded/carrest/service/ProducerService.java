package ua.foxminded.carrest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.model.Producer;
import ua.foxminded.carrest.repository.ProducerRepository;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final ProducerRepository producerRepository;

    public List<Producer> findAll(){
        return producerRepository.findAll();
    }

    public Producer save(Producer producer){
        return producerRepository.save(producer);
    }

    public void delete(Producer producer){
        producerRepository.delete(producer);
    }

    public Producer update(Producer producer){
        return Producer.builder()
            .producerName(producer.getProducerName())
            .id(producer.getId())
            .modelName(producer.getModelName())
            .build();
    }

}
