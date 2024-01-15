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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all cars types")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all cars types",
            content = {@Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = CarTypeDTO.class)))}),
        @ApiResponse(responseCode = "500", description = "Cars types not found",
            content = @Content(mediaType = "application/json"))})
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
    @Operation(summary = "Get car type by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found car type",
            content = {@Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = CarTypeDTO.class)))}),
        @ApiResponse(responseCode = "500", description = "Cars type not found",
            content = @Content(mediaType = "application/json"))})
    public CarTypeDTO getCarTypeById(@PathVariable Long carTypeId){
        CarTypeDTO carTypeDTO = carTypeService.getCarTypeById(carTypeId);
        if (carTypeDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car type not found");
        }
        return carTypeDTO;
    }



    @PutMapping("/{carTypeId}")
    @Operation(summary = "Update car type by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated car type",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = CarTypeDTO.class))}),
        @ApiResponse(responseCode = "404", description = "Car type not found",
            content = @Content(mediaType = "application/json"))})
    public CarTypeDTO updateCarById(@PathVariable Long carTypeId,
                                                 @RequestBody CarType updatedCarType){
         CarTypeDTO carTypeDTO = carTypeService.updateById(carTypeId, updatedCarType);
        if (carTypeDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car type not found");
        }
        return carTypeDTO;
    }

    @DeleteMapping("/{carTypeId}")
    @Operation(summary = "Delete car type by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Car type deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Car type not found",
            content = @Content(mediaType = "application/json"))})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCarType(@PathVariable Long carTypeId){
        carTypeService.deleteById(carTypeId);
    }
}
