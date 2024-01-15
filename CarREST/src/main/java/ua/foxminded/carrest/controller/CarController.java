package ua.foxminded.carrest.controller;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.custom.response.CarSearchResponse;
import ua.foxminded.carrest.dao.dto.CarDTO;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.dto.ProducerDTO;
import ua.foxminded.carrest.service.CarService;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;


    @Operation(summary = "Get all cars")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all cars",
            content = {@Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = CarDTO.class)))}),
        @ApiResponse(responseCode = "500", description = "Cars not found",
            content = @Content(mediaType = "application/json"))})
    @GetMapping
    public CarSearchResponse getAllCars(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy,
                                        @RequestParam(defaultValue = "asc") String sortOrder){

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<CarDTO> carListPage = carService.findCarsPaged(pageable);

        if (carListPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No cars found");
        }

        return CarSearchResponse.builder()
            .carDTOList(carListPage.getContent())
            .currentPage(carListPage.getNumber())
            .pageSize(carListPage.getSize())
            .totalElements(carListPage.getTotalElements())
            .totalPages(carListPage.getTotalPages())
            .build();
    }

    @Operation(summary = "Get car with id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found car with id",
            content = {@Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = CarDTO.class)))}),
        @ApiResponse(responseCode = "500", description = "Car with id not found",
            content = @Content(mediaType = "application/json"))})
    @GetMapping("/{carId}")
    public CarDTO getCarById(@PathVariable Long carId) {
        CarDTO carDTO = carService.findById(carId);
        if (carDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }
        return carDTO;
    }

    @Operation(summary = "Create new car", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car has been created",
            content = @Content)})
    @PostMapping("/producer/{producerName}/models/{modelName}/years/{year}")
    public CarDTO createCar(@PathVariable String producerName,
                         @PathVariable String modelName,
                         @RequestBody Set<CarTypeDTO> carTypeDTOSet,
                         @PathVariable int year){

        ProducerDTO producerDTO = ProducerDTO.builder()
            .producerName(producerName)
            .modelName(modelName)
            .build();

        CarDTO car = CarDTO.builder()
            .carType(carTypeDTOSet)
            .producer(producerDTO)
            .year(year)
            .build();

        return carService.save(car);
    }

    @Operation(summary = "Update car year", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car has been updated",
            content = @Content)})
    @PostMapping("/update/car/{carId}/year/{newCarYear}")
    public CarDTO updateCarYear(@PathVariable Long carId,
                             @PathVariable int newCarYear){

        CarDTO updatedCar = carService.updateCarYear(carId, newCarYear);
        if (updatedCar == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found for year update");
        }
        return updatedCar;
    }

    @Operation(summary = "Update car")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car has been updated",
            content = {@Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = CarDTO.class)))})})
    @PutMapping("/{carId}")
    public CarDTO updateCar(@PathVariable Long carId, @RequestBody CarDTO updatedCar){
        return carService.updateCarById(carId, updatedCar);
    }


    @Operation(summary = "Delete car", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Delete the brand",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Car was not deleted",
            content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "404", description = "Car not found",
            content = @Content(mediaType = "application/json"))})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/producer/{producerName}/models/{modelName}/years/{year}")
    public void deleteCar(@PathVariable String producerName,
                                          @PathVariable String modelName,
                                          @PathVariable int year){

        carService.deleteCarByInfo(producerName, modelName, year);
    }
}
