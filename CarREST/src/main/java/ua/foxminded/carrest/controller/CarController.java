package ua.foxminded.carrest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.model.Car;
import ua.foxminded.carrest.service.CarService;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars(){
        List<Car> carList = carService.findAll();
        return new ResponseEntity<>(carList, HttpStatus.OK);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Long carId){
        Car car = carService.findById(carId);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car newCar){
        Car savedCar = carService.save(newCar);
        return new ResponseEntity<>(savedCar, HttpStatus.OK);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<Car> updateCar(@PathVariable Long carId, @RequestBody Car updatedCar){
        Car modifiedCar = carService.updateCarById(carId, updatedCar);

        return new ResponseEntity<>(modifiedCar, HttpStatus.OK);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId){
        carService.deleteById(carId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
