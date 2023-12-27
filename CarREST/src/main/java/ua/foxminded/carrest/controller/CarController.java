package ua.foxminded.carrest.controller;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.dto.CarDTO;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.dto.ProducerDTO;
import ua.foxminded.carrest.dao.model.Car;
import ua.foxminded.carrest.service.CarService;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public List<CarDTO> getAllCars(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<CarDTO> producerPage = carService.findCarsPaged(pageable);

        return producerPage.getContent();
    }

    @GetMapping("/{carId}")
    public CarDTO getCarById(@PathVariable Long carId){
        return carService.findById(carId);
    }

    @PostMapping("/producer/{producerName}/models/{modelName}/years/{year}")
    public CarDTO createCar(@PathVariable String producerName,
                         @PathVariable String modelName,
                         @RequestBody Set<CarTypeDTO> carTypeDTOSet,
                         @PathVariable int year){

        ProducerDTO producerDTO = ProducerDTO.builder()
            .producerName(producerName)
            .modelName(modelName)
            .build();

        CarDTO car = CarDTO.builder()
            .carTypeEntities(carTypeDTOSet)
            .producerDTO(producerDTO)
            .year(year)
            .build();

        return carService.save(car);
    }

    @PostMapping("/update/car/{carId}/year/{newCarYear}")
    public CarDTO updateCarYear(@PathVariable Long carId,
                             @PathVariable int newCarYear){

       return carService.updateCarYear(carId, newCarYear);
    }

    @PutMapping("/{carId}")
    public CarDTO updateCar(@PathVariable Long carId, @RequestBody Car updatedCar){
        return carService.updateCarById(carId, updatedCar);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/producer/{producerName}/models/{modelName}/years/{year}")
    public void deleteCar(@PathVariable String producerName,
                                          @PathVariable String modelName,
                                          @PathVariable int year){

        carService.deleteCarByInfo(producerName, modelName, year);
    }


    //TODO What is I would like to sort by 2 parameters
    // Usually it is useful to provide an option to specify a sorting order ascending or descending
}
