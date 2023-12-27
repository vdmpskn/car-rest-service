package ua.foxminded.carrest.service;

import java.util.List;
import java.util.Optional;

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

    public List<CarTypeDTO> carTypeList(){
        return carTypeRepository.findAll()
            .stream()
            .map(carTypeConverter::convertToDTO)
            .toList();
    }

    public CarTypeDTO save(CarTypeDTO carType){
        return carTypeConverter.convertToDTO(carTypeRepository.save(carTypeConverter.convertToModel(carType)));
    }

    public Optional<CarTypeDTO> getCarTypeById(Long id){
        return carTypeRepository.findById(id).map(carTypeConverter::convertToDTO);
    }

    public CarTypeDTO updateById(Long id,CarType updatedCarType) {
        Optional<CarTypeDTO> currentCarType = carTypeRepository.findById(id).map(carTypeConverter::convertToDTO);
        currentCarType.get().setCarBodyType(updatedCarType.getCarBodyType());

        return carTypeConverter.convertToDTO(carTypeRepository.save(carTypeConverter.convertToModel(currentCarType.get())));
    }

    public void delete(CarTypeDTO carType){
        carTypeRepository.delete(carTypeConverter.convertToModel(carType));
    }

    public void deleteById(Long id){
       carTypeRepository.delete(carTypeRepository.findById(id).get());
    }

}
