package ua.foxminded.carrest.controller;

import java.util.List;
import java.util.Optional;

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
import ua.foxminded.carrest.custom.response.CarTypeResponse;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.service.CarTypeService;

@RestController
@RequestMapping("/api/v1/car-types")
@RequiredArgsConstructor
public class CarTypeController {

    private final CarTypeService carTypeService;

    @GetMapping
    public ResponseEntity<CarTypeResponse> getAllCarTypes(){
        List<CarType> carTypeList = carTypeService.carTypeList();
        CarTypeResponse carTypeResponse = new CarTypeResponse(carTypeList);

        return new ResponseEntity<>(carTypeResponse, HttpStatus.OK);
    }

    @GetMapping("/{carTypeId}")
    public ResponseEntity<CarType> getCarTypeById(@PathVariable Long carTypeId){
        Optional<CarType> carType = carTypeService.getCarTypeById(carTypeId);

        return new ResponseEntity<>(carType.get(), HttpStatus.OK);
    }

    @PutMapping("/{carTypeId}")
    public ResponseEntity<CarType> updateCarById(@PathVariable Long carTypeId,
                                                 @RequestBody CarType updatedCarType){
        CarType modifiedCarType = carTypeService.updateById(carTypeId, updatedCarType);
        return new ResponseEntity<>(modifiedCarType, HttpStatus.OK);
    }

    @DeleteMapping("/{carTypeId}")
    public ResponseEntity<Void> deleteCarType(@PathVariable Long carTypeId){
        carTypeService.deleteById(carTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
