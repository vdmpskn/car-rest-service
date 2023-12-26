package ua.foxminded.carrest.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<CarType> getCarTypeById(Long id){
        return carTypeRepository.findById(id);
    }

    public CarType updateById(Long id,CarType updatedCarType) {
        Optional<CarType> currentCarType = carTypeRepository.findById(id);
        currentCarType.get().setCarBodyType(updatedCarType.getCarBodyType());

        return carTypeRepository.save(currentCarType.get());
    }

    public void delete(CarType carType){
        carTypeRepository.delete(carType);
    }

    public void deleteById(Long id){
       carTypeRepository.delete(carTypeRepository.findById(id).get());
    }

}
