package ua.foxminded.carrest.controller;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.model.Producer;
import ua.foxminded.carrest.service.ProducerService;

@RestController
@RequestMapping("/api/v1/producers")
@RequiredArgsConstructor
public class ProducerController {
    private final ProducerService producerService;

    @GetMapping
    public List<Producer> getAllProducers(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Producer> producerPage = producerService.findAllPaged(pageable);

        return producerPage.getContent();
    }

    @GetMapping("/{producerName}/models/")
    public List<String> listOfModels(@PathVariable String producerName,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<String> modelNames = producerService.getAllModelsPaged(producerName, pageable);

        return modelNames.getContent();
    }

    @GetMapping("/{producerId}")
    public Producer getProducerById(@PathVariable Long producerId) {
        return producerService.findById(producerId);
    }

    @PostMapping
    public Producer createProducer(@RequestBody Producer producer) {
        return producerService.save(producer);
    }

    @PostMapping("/{producerName}/models/{modelName}")
    public Producer createNewProducerWithNames(@PathVariable String producerName,
                                               @PathVariable String modelName) {
        Producer newProducer = Producer.builder()
            .producerName(producerName)
            .modelName(modelName)
            .build();
        return producerService.save(newProducer);
    }

    @PutMapping("/{producerId}")
    public Producer updateProducer(@PathVariable Long producerId, @RequestBody Producer producer) {
        return producerService.updateById(producerId, producer);
    }

    @PutMapping("/{producerName}/models/{oldModelName}_{newModelName}")
    public Producer updateModel(@PathVariable String producerName,
                                @PathVariable String oldModelName,
                                @PathVariable String newModelName) {

        return producerService.updateModel(producerName, oldModelName, newModelName);
    }

    @DeleteMapping("/{producerName}/models/{modelName}")
    public void deleteModel(@PathVariable String producerName,
                            @PathVariable String modelName) {

        producerService.deleteModel(producerName, modelName);
    }

    @DeleteMapping("/{producerId}")
    public ResponseEntity<Void> deleteProducer(@PathVariable Long producerId) {
        producerService.deleteById(producerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
