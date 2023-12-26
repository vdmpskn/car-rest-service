package ua.foxminded.carrest.controller;

import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
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
import ua.foxminded.carrest.dao.model.Car;
import ua.foxminded.carrest.service.CarService;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final CarService carService;

    @GetMapping("/producer/{producerName}")
    public List<Car> searchByProducerName(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @PathVariable final String producerName) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Car> result = carService.findCarsByProducerName(producerName, pageable);

        return result.getContent();
    }

    @GetMapping("/model/{modelName}")
    public List<Car> searchByModelName(
        @PathVariable String modelName,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Car> result = carService.findCarsByModelName(modelName, pageable);

        return result.getContent();
    }

    @GetMapping("/year/{minYear}_{maxYear}")
    public List<Car> searchByYearRange(
        @PathVariable Integer minYear,
        @PathVariable Integer maxYear,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Car> result = carService.findCarsByYearRange(minYear, maxYear, pageable);

        return result.getContent();
    }

}
