package ua.foxminded.carrest.service;

import java.util.List;
import java.util.Optional;

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

    public void deleteById(Long id){
        producerRepository.deleteById(id);
    }

    public Producer update(Producer producer){
        return Producer.builder()
            .producerName(producer.getProducerName())
            .id(producer.getId())
            .modelName(producer.getModelName())
            .build();
    }

    public Producer updateById(Long id, Producer updatedProducer){
        Optional<Producer> modifiedProducer = producerRepository.findById(id);
        modifiedProducer.get().setProducerName(updatedProducer.getProducerName());
        modifiedProducer.get().setModelName(updatedProducer.getModelName());
        return producerRepository.save(modifiedProducer.get());
    }

    public Producer findById(Long id){
        return producerRepository.findById(id).get();
    }

}
