package ua.foxminded.carrest.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.dto.CarDTO;
import ua.foxminded.carrest.dao.model.Car;

@Component
@RequiredArgsConstructor
public class CarConverter {

    private final ModelMapper modelMapper;

    public CarDTO convertToDTO(Car car) {
        return modelMapper.map(car, CarDTO.class);
    }

    public Car convertToModel(CarDTO carDTO) {
        return modelMapper.map(carDTO, Car.class);
    }
}
