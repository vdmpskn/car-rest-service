package ua.foxminded.carrest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.custom.response.CarSearchResponse;
import ua.foxminded.carrest.dao.dto.CarDTO;
import ua.foxminded.carrest.service.CarService;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final CarService carService;

    @GetMapping("/producer/{producerName}")
    public CarSearchResponse searchByProducerName(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                  @RequestParam(defaultValue = "asc") String sortOrder,
                                                  @PathVariable final String producerName) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,sortBy));
        Page<CarDTO> result = carService.findCarsByProducerName(producerName, pageable);

        return CarSearchResponse.builder()
            .carDTOList(result.getContent())
            .currentPage(result.getNumber())
            .pageSize(result.getSize())
            .totalElements(result.getTotalElements())
            .totalPages(result.getTotalPages()).build();
    }

    @GetMapping("/model/{modelName}")
    public CarSearchResponse searchByModelName(@PathVariable String modelName,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy,
                                          @RequestParam(defaultValue = "asc") String sortOrder) {

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,sortBy));
        Page<CarDTO> result = carService.findCarsByModelName(modelName, pageable);

        return CarSearchResponse.builder()
            .carDTOList(result.getContent())
            .currentPage(result.getNumber())
            .pageSize(result.getSize())
            .totalElements(result.getTotalElements())
            .totalPages(result.getTotalPages()).build();
    }

    @GetMapping("/year/{minYear}_{maxYear}")
    public CarSearchResponse searchByYearRange(@PathVariable Integer minYear,
                                          @PathVariable Integer maxYear,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy,
                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,sortBy));
        Page<CarDTO> result = carService.findCarsByYearRange(minYear, maxYear, pageable);

        return CarSearchResponse.builder()
            .carDTOList(result.getContent())
            .currentPage(result.getNumber())
            .pageSize(result.getSize())
            .totalElements(result.getTotalElements())
            .totalPages(result.getTotalPages()).build();
    }

}
