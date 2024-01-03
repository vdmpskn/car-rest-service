package ua.foxminded.carrest.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ua.foxminded.carrest.converter.CarTypeConverter;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.repository.CarTypeRepository;

@SpringBootTest
class CarTypeServiceTest {
    @Mock
    private CarTypeRepository carTypeRepository;

    @Mock
    private CarTypeConverter carTypeConverter;

    @InjectMocks
    private CarTypeService carTypeService;

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
}
