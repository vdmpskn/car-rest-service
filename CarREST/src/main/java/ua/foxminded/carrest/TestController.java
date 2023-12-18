package ua.foxminded.carrest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.model.Car;
import ua.foxminded.carrest.dao.model.CarBodyType;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.dao.model.Producer;
import ua.foxminded.carrest.repository.CarRepository;
import ua.foxminded.carrest.repository.CarTypeRepository;
import ua.foxminded.carrest.repository.ProducerRepository;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController {
    
    private final CarRepository carRepository;

    private final ProducerRepository producerRepository;

    private final CarTypeRepository carTypeRepository;


    @GetMapping("/api/v1/cars")
    public List<Car> getAllCar() {
        return carRepository.findAll();
    }

    @GetMapping("/api/v1/cartypes")
    public List<CarType> getAllCarTypes() {
        return carTypeRepository.findAll();
    }

    @GetMapping("/api/v1/producers")
    public List<Producer> getAllProducers() {
        return producerRepository.findAll();
    }
    
    @PostMapping
    public ResponseEntity<CarResponse> saveCar(final @RequestBody List<CreateCarRequest> body) {
      List<Car> result = new ArrayList<>();
        for (final CreateCarRequest createCarRequest : body) {
            Producer producer = new Producer();
            producer.setModelName(createCarRequest.getModelName());
            producer.setProducerName(createCarRequest.getProducerName());
            producer = producerRepository.save(producer);

            Car car = new Car();
            car.setCarType(createCarRequest.getCarBodyType().stream().map(e -> {
                CarType carType = new CarType();
                carType.setCarBodyType(e);
                return carTypeRepository.save(carType);
            }).collect(Collectors.toSet()));
            car.setProducer(producer);
            car.setYear(createCarRequest.getYear());

            Car savedCar = carRepository.save(car);
            result.add(savedCar);
        }
        CarResponse response = new CarResponse(result);

       return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
