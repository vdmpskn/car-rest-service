package ua.foxminded.carrest.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<Producer> findAllPaged(Pageable pageable){
        return producerRepository.findAll(pageable);
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

    public Page<String> getAllModelsPaged(String producerName, Pageable pageable) {
        List<Producer> producerList = producerRepository.findByProducerName(producerName);

        List<String> modelNames = producerList.stream()
            .map(Producer::getModelName)
            .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), modelNames.size());

        return new PageImpl<>(modelNames.subList(start, end), pageable, modelNames.size());
    }

    @Transactional
    public void deleteModel(String producerName, String modelName) {
        Optional<Producer> producerOptional = producerRepository.findByProducerName(producerName).stream()
            .filter(producer -> producer.getModelName().equals(modelName))
            .findFirst();

        producerOptional.ifPresent(producer -> producerRepository.deleteByModelName(producer.getModelName()));
    }

    public Producer updateById(Long id, Producer updatedProducer){
        Optional<Producer> modifiedProducer = producerRepository.findById(id);
        modifiedProducer.get().setProducerName(updatedProducer.getProducerName());
        modifiedProducer.get().setModelName(updatedProducer.getModelName());
        return producerRepository.save(modifiedProducer.get());
    }

    public Producer updateModel(String producerName, String oldModelName, String newModelName) {
        List<Producer> producers = producerRepository.findByProducerName(producerName);

        for (Producer producer : producers) {
            if (producer.getModelName().equals(oldModelName)) {
                producer.setModelName(newModelName);
                producerRepository.save(producer);
                return producer;
            }
        }

        throw new RuntimeException("Producer not found for the given model name: " + oldModelName);
    }

    public Producer findById(Long id){
        return producerRepository.findById(id).get();
    }

}
