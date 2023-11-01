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

import ua.foxminded.carrest.dao.model.Car;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.dao.model.Producer;
import ua.foxminded.carrest.repository.CarRepository;
import ua.foxminded.carrest.repository.CarTypeRepository;
import ua.foxminded.carrest.repository.ProducerRepository;

@RestController
@RequestMapping("/")
public class TestController {
    
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private CarTypeRepository carTypeRepository;


    @GetMapping
    public ResponseEntity<List<Car>> getAllCar() {
        return new ResponseEntity<>(carRepository.findAll(), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<List<Car>> saveCar(final @RequestBody List<CreateCarRequest> body) {
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

       return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
