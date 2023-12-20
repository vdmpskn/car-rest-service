package ua.foxminded.carrest.converter;

import org.modelmapper.ModelMapper;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.entity.CarEntity;
import ua.foxminded.carrest.dao.model.Car;

@RequiredArgsConstructor
public class CarConverter {
    private final ModelMapper modelMapper;

    public CarEntity convertToEntity(Car car) {
        return modelMapper.map(car, CarEntity.class);
    }

    public Car convertToModel(CarEntity carEntity) {
        return modelMapper.map(carEntity, Car.class);
    }
}
