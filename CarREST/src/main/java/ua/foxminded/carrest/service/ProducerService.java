package ua.foxminded.carrest.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.converter.ProducerConverter;
import ua.foxminded.carrest.dao.dto.ProducerDTO;
import ua.foxminded.carrest.dao.model.Producer;
import ua.foxminded.carrest.repository.ProducerRepository;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final ProducerRepository producerRepository;

    private final ProducerConverter producerConverter;

    public Page<ProducerDTO> findAllPaged(Pageable pageable){
        return producerRepository.findAll(pageable).map(producerConverter::convertToDTO);
    }

    public ProducerDTO save(Producer producer){
        return producerConverter.convertToDTO(producerRepository.save(producer));
    }

    public void deleteById(Long id){
        producerRepository.deleteById(id);
    }

    public Page<String> getAllModelsPaged(String producerName, Pageable pageable) {
        List<ProducerDTO> producerList = producerRepository.findByProducerName(producerName)
            .stream()
            .map(producerConverter::convertToDTO)
            .toList();

        List<String> modelNames = producerList.stream()
            .map(ProducerDTO::getModelName)
            .toList();

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

    @Transactional
    public ProducerDTO updateById(Long id, Producer updatedProducer) {
        Optional<ProducerDTO> optionalModifiedProducer = producerRepository.findById(id).map(producerConverter::convertToDTO);

        if (optionalModifiedProducer.isPresent()) {
            ProducerDTO modifiedProducer = optionalModifiedProducer.get();
            modifiedProducer.setProducerName(updatedProducer.getProducerName());
            modifiedProducer.setModelName(updatedProducer.getModelName());
            return producerConverter.convertToDTO(producerRepository.save(producerConverter.convertToModel(modifiedProducer)));
        }
        throw new EntityNotFoundException("Producer with ID " + id + " not found");
    }

    public ProducerDTO updateModel(String producerName, String oldModelName, String newModelName) {
        List<ProducerDTO> producers = producerRepository.findByProducerName(producerName)
            .stream()
            .map(producerConverter::convertToDTO)
            .toList();

        for (ProducerDTO producer : producers) {
            if (producer.getModelName().equals(oldModelName)) {
                producer.setModelName(newModelName);
                producerConverter.convertToDTO(producerRepository.save(producerConverter.convertToModel(producer)));
                return producer;
            }
        }

        throw new RuntimeException("Producer not found for the given model name: " + oldModelName);
    }

    public ProducerDTO findById(Long id) {
        Producer producer = producerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producer with id " + id + " not found"));

        return producerConverter.convertToDTO(producer);
    }
}
