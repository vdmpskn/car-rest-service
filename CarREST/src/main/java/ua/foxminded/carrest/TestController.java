package ua.foxminded.carrest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.custom.response.CarResponse;
import ua.foxminded.carrest.dao.model.Car;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.dao.model.Producer;
import ua.foxminded.carrest.service.CarService;
import ua.foxminded.carrest.service.CarTypeService;
import ua.foxminded.carrest.service.ProducerService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController {
    
    private final CarService carService;

    private final ProducerService producerService;

    private final CarTypeService carTypeService;

    
    @PostMapping
    public ResponseEntity<CarResponse> saveCar(final @RequestBody List<CreateCarRequest> body) {
      List<Car> result = new ArrayList<>();
        for (final CreateCarRequest createCarRequest : body) {
            Producer producer = new Producer();
            producer.setModelName(createCarRequest.getModelName());
            producer.setProducerName(createCarRequest.getProducerName());
            producer = producerService.save(producer);

            Car car = new Car();
            car.setCarType(createCarRequest.getCarBodyType().stream()
                .flatMap(e -> {
                    CarType carType = new CarType();
                    carType.setCarBodyType(e);
                    carTypeService.save(carType);
                    return Stream.of(carType);
                })
                .collect(Collectors.toSet()));

            car.setProducer(producer);
            car.setYear(createCarRequest.getYear());

            Car savedCar = carService.save(car);
            result.add(savedCar);
        }
        CarResponse response = new CarResponse(result);

       return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
