package ua.foxminded.carrest.service;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.converter.CarTypeConverter;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.repository.CarTypeRepository;

@Service
@RequiredArgsConstructor
public class CarTypeService {
    private final CarTypeRepository carTypeRepository;

    private final CarTypeConverter carTypeConverter;

    public Page<CarTypeDTO> carTypeList(Pageable pageable){
        return carTypeRepository.findAll(pageable).map(carTypeConverter::convertToDTO);
    }

    public CarTypeDTO save(CarTypeDTO carType){
        return carTypeConverter.convertToDTO(carTypeRepository.save(carTypeConverter.convertToModel(carType)));
    }

    public CarTypeDTO getCarTypeById(Long id) {
        Optional<CarType> carTypeOptional = carTypeRepository.findById(id);

        if (carTypeOptional.isPresent()) {
            CarType carType = carTypeOptional.get();

            return carTypeConverter.convertToDTO(carType);
        } else {
            throw new EntityNotFoundException("CarType with ID " + id + " not found");
        }
    }

    public CarTypeDTO updateById(Long id, CarType updatedCarType) {
        Optional<CarTypeDTO> currentCarTypeOptional = carTypeRepository.findById(id).map(carTypeConverter::convertToDTO);

        if (currentCarTypeOptional.isPresent()) {
            CarTypeDTO currentCarType = currentCarTypeOptional.get();
            currentCarType.setCarBodyType(updatedCarType.getCarBodyType());

            return carTypeConverter.convertToDTO(carTypeRepository.save(carTypeConverter.convertToModel(currentCarType)));
        } else {
            throw new EntityNotFoundException("CarType with id " + id + " not found");
        }
    }

    public void deleteById(Long id) {
        Optional<CarType> carTypeOptional = carTypeRepository.findById(id);

        if (carTypeOptional.isPresent()) {
            carTypeRepository.delete(carTypeOptional.get());
        } else {
            throw new EntityNotFoundException("CarType with ID " + id + " not found");
        }
    }
}
