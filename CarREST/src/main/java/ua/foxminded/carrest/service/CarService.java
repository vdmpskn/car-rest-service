package ua.foxminded.carrest.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.converter.CarConverter;
import ua.foxminded.carrest.dao.dto.CarDTO;
import ua.foxminded.carrest.dao.model.Car;
import ua.foxminded.carrest.repository.CarRepository;

@Service
@RequiredArgsConstructor
public class CarService{

    private final CarRepository carRepository;

    private final CarConverter converter;


    public Page<CarDTO> findCarsPaged(Pageable pageable){
         return carRepository.findAll(pageable).map(converter::convertToDTO);
    }

    public Page<CarDTO> findCarsByProducerName(String producerName, Pageable pageable){
        return carRepository.findByProducer_ProducerName(producerName, pageable)
            .map(converter::convertToDTO);
    }

    public Page<CarDTO> findCarsByModelName(String modelName, Pageable pageable){
        return carRepository.findByProducer_ModelName(modelName, pageable)
            .map(converter::convertToDTO);
    }

    public Page<CarDTO> findCarsByYearRange(Integer minYear, Integer maxYear, Pageable pageable){
        return carRepository.findByYearBetween(minYear,maxYear, pageable)
            .map(converter::convertToDTO);
    }

    public CarDTO updateCarYear(Long carId, int newCarYear){

       Optional<CarDTO> modifiedCar = carRepository.findById(carId).map(converter::convertToDTO);

       modifiedCar.get().setYear(newCarYear);

       return modifiedCar.get();
    }

    public CarDTO findById(Long carId) {
        Car car = carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException("Car with id " + carId + " not found"));

        return converter.convertToDTO(car);
    }


    public CarDTO updateCarById(Long carId, CarDTO updatedCar){
        Optional<CarDTO> currentCar = carRepository.findById(carId).map(converter::convertToDTO);

        currentCar.get().setCarType(updatedCar.getCarType());
        currentCar.get().setYear(updatedCar.getYear());
        currentCar.get().setProducer(updatedCar.getProducer());

        return converter.convertToDTO(carRepository.save(converter.convertToModel(currentCar.get())));
    }

    @Transactional
    public void deleteCarByInfo(String producerName,
                                String modelName,
                                int year){
        List<Car> carsToDelete = carRepository.findCarByProducer_ProducerNameAndProducer_ModelNameAndYear(producerName, modelName, year);

        carRepository.deleteAll(carsToDelete);
    }

    public CarDTO save(CarDTO newCar){
         return converter.convertToDTO(carRepository.save(converter.convertToModel(newCar)));
    }
}
