package ua.foxminded.carrest.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.converter.CarConverter;
import ua.foxminded.carrest.dao.dto.CarDTO;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.model.Car;
import ua.foxminded.carrest.dao.model.CarBodyType;
import ua.foxminded.carrest.repository.CarRepository;

@Service
@RequiredArgsConstructor
public class CarService{

    private final CarRepository carRepository;

    private final CarConverter converter;

    public List<Car> findAll(){
        return carRepository.findAll();
    }

    public Page<Car> findCarsPaged(Pageable pageable){
        return carRepository.findAll(pageable);
    }

    public Page<Car> findCarsByProducerName(String producerName, Pageable pageable){
        return carRepository.findByProducer_ProducerName(producerName, pageable);
    }

    public Page<Car> findCarsByModelName(String modelName, Pageable pageable){
        return carRepository.findByProducer_ModelName(modelName, pageable);
    }

    public Page<Car> findCarsByYearRange(Integer minYear, Integer maxYear, Pageable pageable){
        return carRepository.findByYearBetween(minYear,maxYear, pageable);
    }

    public Optional<Car> updateCarYear(int currentCarYear, int newCarYear){
        Optional<Car> modifiedCar = carRepository.findCarByYear(currentCarYear);
        modifiedCar.get().setYear(newCarYear);

        return modifiedCar;

    }

    public Car findById(Long carId){
        return carRepository.findById(carId).get();
    }

    public void deleteById(Long id){
        carRepository.deleteById(id);
    }

    public Car updateCar(Car oldCar){
        return Car.builder()
            .id(oldCar.getId())
            .carType(oldCar.getCarType())
            .year(oldCar.getYear())
            .producer(oldCar.getProducer())
            .build();
    }

    public Car updateCarById(Long carId, Car updatedCar){
        Car currentCar = carRepository.findById(carId).get();

        currentCar.setCarType(updatedCar.getCarType());
        currentCar.setYear(updatedCar.getYear());
        currentCar.setProducer(updatedCar.getProducer());

        return carRepository.save(currentCar);
    }

    @Transactional
    public void deleteCarByInfo(String producerName,
                                String modelName,
                                int year){
        List<Car> carsToDelete = carRepository.findCarByProducer_ProducerNameAndProducer_ModelNameAndYear(producerName, modelName, year);

        carRepository.deleteAll(carsToDelete);
    }

    public Car save(CarDTO newCar){
         return carRepository.save(converter.convertToModel(newCar));
    }
}
