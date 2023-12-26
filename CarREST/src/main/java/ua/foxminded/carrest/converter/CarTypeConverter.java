package ua.foxminded.carrest.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.model.CarType;

@Component
@RequiredArgsConstructor
public class CarTypeConverter {

    private final ModelMapper modelMapper;

    public CarTypeDTO convertToDTO(CarType carType) {
        return modelMapper.map(carType, CarTypeDTO.class);
    }

    public CarType convertToModel(CarTypeDTO carTypeDTO) {
        return modelMapper.map(carTypeDTO, CarType.class);
    }
}
