package ua.foxminded.carrest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import ua.foxminded.carrest.converter.ProducerConverter;
import ua.foxminded.carrest.dao.dto.ProducerDTO;
import ua.foxminded.carrest.dao.model.Producer;
import ua.foxminded.carrest.repository.ProducerRepository;


class ProducerServiceTest {
    @InjectMocks
    private ProducerService producerService;

    @Mock
    private ProducerRepository producerRepository;

    @Mock
    private ProducerConverter producerConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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

    @Test
    void shouldNotUpdateById_InvalidId() {
        when(producerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> producerService.updateById(999L, new Producer()));
    }


    @Test
    void shouldNotUpdateModel_NullOldModelName() {
        assertThrows(RuntimeException.class, () -> producerService.updateModel("producerName", null, "newModelName"));
    }

    @Test
    void shouldNotUpdateModel_NullNewModelName() {
        assertThrows(RuntimeException.class, () -> producerService.updateModel("producerName", "oldModelName", null));
    }

    @Test
    void shouldNotUpdateModel_NoProducerFound() {
        when(producerRepository.findByProducerName("nonexistentProducer")).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> producerService.updateModel("nonexistentProducer", "oldModelName", "newModelName"));
    }

    @Test
    void shouldNotUpdateModel_NoModelFound() {
        when(producerRepository.findByProducerName("producerName")).thenReturn(Collections.singletonList(new Producer()));

        assertThrows(RuntimeException.class, () -> producerService.updateModel("producerName", "nonexistentModel", "newModelName"));
    }
}
