package ua.foxminded.carrest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.custom.response.CarTypeSearchResponse;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.service.CarTypeService;

@RestController
@RequestMapping("/api/v1/car-types")
@RequiredArgsConstructor
public class CarTypeController {

    private final CarTypeService carTypeService;

    @GetMapping
    public CarTypeSearchResponse getAllCarTypes(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sortBy,
                                                @RequestParam(defaultValue = "asc") String sortOrder){

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<CarTypeDTO> carTypeDTOPage = carTypeService.carTypeList(pageable);

        if (carTypeDTOPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No car types found");
        }

        return CarTypeSearchResponse.builder()
            .carDTOList(carTypeDTOPage.getContent())
            .currentPage(carTypeDTOPage.getNumber())
            .pageSize(carTypeDTOPage.getSize())
            .totalElements(carTypeDTOPage.getTotalElements())
            .totalPages(carTypeDTOPage.getTotalPages())
            .build();
    }

    @GetMapping("/{carTypeId}")
    public CarTypeDTO getCarTypeById(@PathVariable Long carTypeId){
        CarTypeDTO carTypeDTO = carTypeService.getCarTypeById(carTypeId);
        if (carTypeDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car type not found");
        }
        return carTypeDTO;
    }

    @PutMapping("/{carTypeId}")
    public CarTypeDTO updateCarById(@PathVariable Long carTypeId,
                                                 @RequestBody CarType updatedCarType){
         CarTypeDTO carTypeDTO = carTypeService.updateById(carTypeId, updatedCarType);
        if (carTypeDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car type not found");
        }
        return carTypeDTO;
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{carTypeId}")
    public void deleteCarType(@PathVariable Long carTypeId){
        carTypeService.deleteById(carTypeId);
    }
}
