package ua.foxminded.carrest.controller;

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

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.custom.response.ModelSearchResponse;
import ua.foxminded.carrest.custom.response.ProducerSearchResponse;
import ua.foxminded.carrest.dao.dto.ProducerDTO;
import ua.foxminded.carrest.dao.model.Producer;
import ua.foxminded.carrest.service.ProducerService;

@RestController
@RequestMapping("/api/v1/producers")
@RequiredArgsConstructor
public class ProducerController {
    private final ProducerService producerService;

    @GetMapping
    public ProducerSearchResponse getAllProducers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                  @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,sortBy));

        Page<ProducerDTO> producerPage = producerService.findAllPaged(pageable);

        return ProducerSearchResponse.builder()
            .producers(producerPage.getContent())
            .currentPage(producerPage.getNumber())
            .pageSize(producerPage.getSize())
            .totalElements(producerPage.getTotalElements())
            .totalPages(producerPage.getTotalPages())
            .build();
    }

    @GetMapping("/{producerName}/models/")
    public ModelSearchResponse listOfModels(@PathVariable String producerName,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String sortBy,
                                            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<String> modelNames = producerService.getAllModelsPaged(producerName, pageable);

        return ModelSearchResponse.builder()
            .models(modelNames.getContent())
            .currentPage(modelNames.getNumber())
            .pageSize(modelNames.getSize())
            .totalElements(modelNames.getTotalElements())
            .totalPages(modelNames.getTotalPages()).build();
    }

    @GetMapping("/{producerId}")
    public ProducerDTO getProducerById(@PathVariable Long producerId) {
        return producerService.findById(producerId);
    }

    @PostMapping
    public ProducerDTO createProducer(@RequestBody Producer producer) {
        return producerService.save(producer);
    }

    @PostMapping("/{producerName}/models/{modelName}")
    public ProducerDTO createNewProducerWithNames(@PathVariable String producerName,
                                                  @PathVariable String modelName) {
        Producer newProducer = Producer.builder()
            .producerName(producerName)
            .modelName(modelName)
            .build();
        return producerService.save(newProducer);
    }

    @PutMapping("/{producerId}")
    public ProducerDTO updateProducer(@PathVariable Long producerId, @RequestBody Producer producer) {
        return producerService.updateById(producerId, producer);
    }

    @PutMapping("/{producerName}/models/{oldModelName}_{newModelName}")
    public ProducerDTO updateModel(@PathVariable String producerName,
                                   @PathVariable String oldModelName,
                                   @PathVariable String newModelName) {

        return producerService.updateModel(producerName, oldModelName, newModelName);
    }

    @DeleteMapping("/{producerName}/models/{modelName}")
    public void deleteModel(@PathVariable String producerName,
                            @PathVariable String modelName) {

        producerService.deleteModel(producerName, modelName);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{producerId}")
    public void deleteProducer(@PathVariable Long producerId) {
        producerService.deleteById(producerId);
    }
}
