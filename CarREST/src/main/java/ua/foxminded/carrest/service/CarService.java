package ua.foxminded.carrest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.model.Car;
import ua.foxminded.carrest.repository.CarRepository;

@Service
@RequiredArgsConstructor
public class CarService{

    private final CarRepository carRepository;

    public List<Car> findAll(){
        return carRepository.findAll();
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

    public Car save(Car newCar){
         return carRepository.save(newCar);
    }
}
