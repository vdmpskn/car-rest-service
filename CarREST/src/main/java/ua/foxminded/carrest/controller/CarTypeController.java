package ua.foxminded.carrest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.service.CarTypeService;

@RestController
@RequestMapping("/api/v1/car-types")
@RequiredArgsConstructor
public class CarTypeController {

    private final CarTypeService carTypeService;

    @GetMapping
    public List<CarType> getAllCarTypes(){
        return carTypeService.carTypeList();
    }

    @GetMapping("/{carTypeId}")
    public CarType getCarTypeById(@PathVariable Long carTypeId){
        return carTypeService.getCarTypeById(carTypeId).get();
    }

    @PutMapping("/{carTypeId}")
    public CarType updateCarById(@PathVariable Long carTypeId,
                                                 @RequestBody CarType updatedCarType){
        return carTypeService.updateById(carTypeId, updatedCarType);
    }

    @DeleteMapping("/{carTypeId}")
    public ResponseEntity<Void> deleteCarType(@PathVariable Long carTypeId){
        carTypeService.deleteById(carTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
