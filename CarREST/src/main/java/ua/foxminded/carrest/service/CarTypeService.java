package ua.foxminded.carrest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.repository.CarTypeRepository;

@Service
@RequiredArgsConstructor
public class CarTypeService {
    private final CarTypeRepository carTypeRepository;

    public List<CarType> carTypeList(){
        return carTypeRepository.findAll();
    }

    public void save(CarType carType){
        carTypeRepository.save(carType);
    }

    public CarType update(CarType carType) {
    return carTypeRepository.save(carType);
    }

    public void delete(CarType carType){
        carTypeRepository.delete(carType);
    }

}
