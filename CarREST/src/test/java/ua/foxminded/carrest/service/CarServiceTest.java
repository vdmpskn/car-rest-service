package ua.foxminded.carrest.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ua.foxminded.carrest.converter.CarConverter;
import ua.foxminded.carrest.dao.dto.CarDTO;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.model.Car;
import ua.foxminded.carrest.dao.model.CarBodyType;
import ua.foxminded.carrest.repository.CarRepository;

@SpringBootTest
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarConverter converter;

    @InjectMocks
    private CarService carService;

    @Test
    void shouldFindCarsPaged() {
        Pageable pageable = Pageable.unpaged();
        when(carRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));
        when(converter.convertToDTO(any())).thenReturn(new CarDTO());

        Page<CarDTO> result = carService.findCarsPaged(pageable);

        assertEquals(0, result.getTotalElements());
    }

    @Test
    void shouldFindCarsByProducerName() {
        String producerName = "Toyota";
        Pageable pageable = Pageable.unpaged();
        when(carRepository.findByProducer_ProducerName(eq(producerName), any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        when(converter.convertToDTO(any())).thenReturn(new CarDTO());

        Page<CarDTO> result = carService.findCarsByProducerName(producerName, pageable);

        assertEquals(0, result.getTotalElements());
    }

    @Test
    void shouldFindCarsByModelName() {
        String modelName = "Camry";
        Pageable pageable = Pageable.unpaged();
        when(carRepository.findByProducer_ModelName(eq(modelName), any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        when(converter.convertToDTO(any())).thenReturn(new CarDTO());

        Page<CarDTO> result = carService.findCarsByModelName(modelName, pageable);

        assertEquals(0, result.getTotalElements());
    }

    @Test
    void shouldFindCarsByYearRange() {
        int minYear = 2010;
        int maxYear = 2020;
        Pageable pageable = Pageable.unpaged();
        when(carRepository.findByYearBetween(eq(minYear), eq(maxYear), any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        when(converter.convertToDTO(any())).thenReturn(new CarDTO());

        Page<CarDTO> result = carService.findCarsByYearRange(minYear, maxYear, pageable);

        assertEquals(0, result.getTotalElements());
    }

    @Test
    void shouldUpdateCarYear() {
        Long carId = 1L;
        int newCarYear = 2022;

        CarDTO carDTO = new CarDTO();
        carDTO.setYear(2021);

        when(carRepository.findById(eq(carId))).thenReturn(Optional.of(new Car()));
        when(converter.convertToDTO(any())).thenReturn(carDTO);

        CarDTO result = carService.updateCarYear(carId, newCarYear);

        assertEquals(newCarYear, result.getYear());
    }

    @Test
    void shouldFindByIdWhenCarExists() {
        Long carId = 1L;
        Car car = new Car();
        CarDTO carDTO = new CarDTO();

        when(carRepository.findById(eq(carId))).thenReturn(Optional.of(car));
        when(converter.convertToDTO(eq(car))).thenReturn(carDTO);

        CarDTO result = carService.findById(carId);

        verify(carRepository, times(1)).findById(eq(carId));
        verify(converter, times(1)).convertToDTO(eq(car));
        assertEquals(carDTO, result);
    }

    @Test
    void shouldUpdateCarById() {
        Long carId = 1L;
        CarDTO updatedCar = new CarDTO();
        Set<CarTypeDTO> carTypeDTOFirstSet= new HashSet<>();
        carTypeDTOFirstSet.add(CarTypeDTO.builder()
            .id(1L)
            .carBodyType(CarBodyType.WAGON).build());
        updatedCar.setCarType(carTypeDTOFirstSet);
        updatedCar.setYear(2022);

        CarDTO currentCarDTO = new CarDTO();
        Set<CarTypeDTO> carTypeDTOSet= new HashSet<>();
        carTypeDTOSet.add(CarTypeDTO.builder()
            .id(1L)
            .carBodyType(CarBodyType.HATCHBACK).build());
        currentCarDTO.setCarType(carTypeDTOSet);
        currentCarDTO.setYear(2020);

        when(carRepository.findById(eq(carId))).thenReturn(Optional.of(new Car()));
        when(converter.convertToDTO(any())).thenReturn(currentCarDTO);
        when(converter.convertToModel(any())).thenReturn(new Car());

        CarDTO result = carService.updateCarById(carId, updatedCar);

        assertEquals(updatedCar.getCarType(), result.getCarType());
        assertEquals(updatedCar.getYear(), result.getYear());
    }

    @Test
    void shouldDeleteCarByInfo() {
        String producerName = "Toyota";
        String modelName = "Camry";
        int year = 2020;

        when(carRepository.findCarByProducer_ProducerNameAndProducer_ModelNameAndYear(eq(producerName), eq(modelName), eq(year)))
            .thenReturn(Collections.singletonList(new Car()));

        carService.deleteCarByInfo(producerName, modelName, year);

    }

    @Test
    void shouldSave() {
        CarDTO newCarDTO = new CarDTO();
        Set<CarTypeDTO> carTypeDTOSet = new HashSet<>();
        carTypeDTOSet.add(CarTypeDTO.builder()
            .carBodyType(CarBodyType.COUPE)
            .id(1L)
            .build());
        newCarDTO.setCarType(carTypeDTOSet);
        newCarDTO.setYear(2022);

        when(converter.convertToModel(any())).thenReturn(new Car());
        when(converter.convertToDTO(any())).thenReturn(newCarDTO);
        when(carRepository.save(any())).thenReturn(new Car());

        CarDTO result = carService.save(newCarDTO);

        assertEquals(newCarDTO.getCarType(), result.getCarType());
        assertEquals(newCarDTO.getYear(), result.getYear());
    }
}
