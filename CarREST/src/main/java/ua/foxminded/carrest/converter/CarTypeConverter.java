package ua.foxminded.carrest.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.entity.CarTypeEntity;
import ua.foxminded.carrest.dao.model.CarType;

@Component
@RequiredArgsConstructor
public class CarTypeConverter {

    private final ModelMapper modelMapper;

    public CarTypeEntity convertToEntity(CarType carType) {
        return modelMapper.map(carType, CarTypeEntity.class);
    }

    public CarType convertToModel(CarTypeEntity carTypeEntity) {
        return modelMapper.map(carTypeEntity, CarType.class);
    }
}
