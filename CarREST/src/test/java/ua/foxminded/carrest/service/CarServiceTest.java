package ua.foxminded.carrest.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import ua.foxminded.carrest.converter.CarConverter;
import ua.foxminded.carrest.dao.dto.CarDTO;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.model.Car;
import ua.foxminded.carrest.dao.model.CarBodyType;
import ua.foxminded.carrest.repository.CarRepository;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarConverter converter;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
    void shouldUpdateCarYearSuccessfully() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setYear(2021);
        Optional<Car> carOptional = Optional.of(car);

        Car updatedCar = new Car();
        updatedCar.setId(1L);
        updatedCar.setYear(2023);

        CarDTO expectedCarDTO = new CarDTO();
        expectedCarDTO.setId(1L);
        expectedCarDTO.setYear(2023);

        when(carRepository.findById(1L)).thenReturn(carOptional);
        when(carRepository.save(updatedCar)).thenReturn(updatedCar);
        when(converter.convertToDTO(updatedCar)).thenReturn(expectedCarDTO);

        CarDTO actualCarDTO = carService.updateCarYear(1L, 2023);

        assertEquals(expectedCarDTO, actualCarDTO);

        verify(carRepository).findById(1L);
        verify(carRepository).save(updatedCar);
        verify(converter).convertToDTO(updatedCar);
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

    private Pageable createPageable() {
        return PageRequest.of(0, 10);
    }

    @Test
    void shouldNotFindCarsByInvalidProducerName() {
        when(carRepository.findByProducer_ProducerName("invalidName", createPageable())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> carService.findCarsByProducerName("invalidName", createPageable()));
    }

    @Test
    void shouldNotFindCarsByInvalidModelName() {
        when(carRepository.findByProducer_ModelName("invalidModel", createPageable())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> carService.findCarsByModelName("invalidModel", createPageable()));
    }

    @Test
    void shouldNotFindCarsByInvalidYearRange() {
        when(carRepository.findByYearBetween(2022, 2021, createPageable())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> carService.findCarsByYearRange(2022, 2021, createPageable()));
    }

    @Test
    void shouldNotUpdateCarYear_InvalidCarId() {
        when(carRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.updateCarYear(999L, 2022));
    }

    @Test
    void shouldNotFindCarById_InvalidCarId() {
        when(carRepository.findById(999L)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> carService.findById(999L));
    }

    @Test
    void shouldNotUpdateCarById_InvalidCarId() {
        when(carRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.updateCarById(999L, new CarDTO()));
    }

    @Test
    void shouldNotDeleteCarByInfo_NoCarsFound() {
        when(carRepository.findCarByProducer_ProducerNameAndProducer_ModelNameAndYear("invalidName", "invalidModel", 2022))
            .thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> carService.deleteCarByInfo("invalidName", "invalidModel", 2022));
    }
}
