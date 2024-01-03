package ua.foxminded.carrest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import ua.foxminded.carrest.converter.ProducerConverter;
import ua.foxminded.carrest.dao.dto.ProducerDTO;
import ua.foxminded.carrest.dao.model.Producer;
import ua.foxminded.carrest.repository.ProducerRepository;

@SpringBootTest
class ProducerServiceTest {

    @Mock
    private ProducerRepository producerRepository;

    @Mock
    private ProducerConverter producerConverter;

    @InjectMocks
    private ProducerService producerService;

    @Test
    void shouldFindAllPaged() {
        Pageable pageable = Pageable.unpaged();
        when(producerRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));
        when(producerConverter.convertToDTO(any())).thenReturn(new ProducerDTO());

        Page<ProducerDTO> result = producerService.findAllPaged(pageable);

        assertEquals(0, result.getTotalElements());
    }

    @Test
    void shouldSave() {
        Producer producer = new Producer();
        when(producerRepository.save(any())).thenReturn(new Producer());
        when(producerConverter.convertToDTO(any())).thenReturn(new ProducerDTO());

        ProducerDTO result = producerService.save(producer);

        assertEquals(producer.getProducerName(), result.getProducerName());
        assertEquals(producer.getModelName(), result.getModelName());
    }

    @Test
    void shouldDeleteById() {
        Long id = 1L;

        producerService.deleteById(id);

       verify(producerRepository).deleteById(eq(id));
    }

    @Test
    void shouldUpdateById() {
        Long id = 1L;
        Producer updatedProducer = new Producer();
        updatedProducer.setProducerName("Updated Producer");
        updatedProducer.setModelName("Updated Model");

        ProducerDTO currentProducerDTO = new ProducerDTO();
        currentProducerDTO.setProducerName("Original Producer");
        currentProducerDTO.setModelName("Original Model");

        when(producerRepository.findById(eq(id))).thenReturn(Optional.of(new Producer()));
        when(producerConverter.convertToDTO(any())).thenReturn(currentProducerDTO);
        when(producerConverter.convertToModel(any())).thenReturn(new Producer());
        when(producerRepository.save(any())).thenReturn(new Producer());

        ProducerDTO result = producerService.updateById(id, updatedProducer);

        assertEquals(updatedProducer.getProducerName(), result.getProducerName());
        assertEquals(updatedProducer.getModelName(), result.getModelName());
    }

    @Test
    void shouldUpdateModel() {
        String producerName = "Toyota";
        String oldModelName = "Camry";
        String newModelName = "Corolla";

        ProducerDTO producerDTO = new ProducerDTO();
        producerDTO.setProducerName(producerName);
        producerDTO.setModelName(oldModelName);

        when(producerRepository.findByProducerName(eq(producerName))).thenReturn(Collections.singletonList(new Producer()));
        when(producerConverter.convertToDTO(any())).thenReturn(producerDTO);
        when(producerConverter.convertToModel(any())).thenReturn(new Producer());
        when(producerRepository.save(any())).thenReturn(new Producer());

        ProducerDTO result = producerService.updateModel(producerName, oldModelName, newModelName);

        assertEquals(newModelName, result.getModelName());
    }

}
