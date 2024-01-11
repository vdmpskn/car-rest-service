package ua.foxminded.carrest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.NoSuchElementException;
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

import ua.foxminded.carrest.converter.CarTypeConverter;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.repository.CarTypeRepository;

class CarTypeServiceTest {

    @Mock
    private CarTypeRepository carTypeRepository;

    @Mock
    private CarTypeConverter carTypeConverter;

    @InjectMocks
    private CarTypeService carTypeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCarTypeList() {
        when(carTypeRepository.findAll(any(Pageable.class)))
            .thenReturn(new PageImpl<>(Collections.singletonList(new CarType())));

        when(carTypeConverter.convertToDTO(any(CarType.class))).thenReturn(new CarTypeDTO());

        Page<CarTypeDTO> result = carTypeService.carTypeList(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }


    @Test
    void shouldDeleteCarTypeById() {
        Long id = 1L;

        when(carTypeRepository.findById(eq(id))).thenReturn(Optional.of(new CarType()));

        carTypeService.deleteById(id);


        verify(carTypeRepository, times(1)).delete(any());
    }
    @Test
    void shouldNotGetCarTypeById_InvalidId() {
        when(carTypeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carTypeService.getCarTypeById(999L));
    }

    @Test
    void shouldNotUpdateById_InvalidId() {
        when(carTypeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carTypeService.updateById(999L, new CarType()));
    }

    @Test
    void shouldNotDeleteById_InvalidId() {
        when(carTypeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carTypeService.deleteById(999L));
    }

    @Test
    void shouldNotDeleteById_NullOptional() {
        when(carTypeRepository.findById(999L)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> carTypeService.deleteById(999L));
    }

    @Test
    void shouldNotDeleteById_EmptyOptional() {
        when(carTypeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carTypeService.deleteById(999L));
    }
}
