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

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all producers with pagination and sorting")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found producers",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ProducerSearchResponse.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(mediaType = "application/json"))})
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
    @Operation(summary = "Get list of models by producer name with pagination and sorting")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found models",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ModelSearchResponse.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Producer not found",
            content = @Content(mediaType = "application/json"))})
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
    @Operation(summary = "Get producer by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found producer",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ProducerDTO.class))}),
        @ApiResponse(responseCode = "404", description = "Producer not found",
            content = @Content(mediaType = "application/json"))})
    public ProducerDTO getProducerById(@PathVariable Long producerId) {
        return producerService.findById(producerId);
    }

    @PostMapping
    @Operation(summary = "Create a new producer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producer created successfully",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ProducerDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(mediaType = "application/json"))})
    @ResponseStatus(HttpStatus.CREATED)
    public ProducerDTO createProducer(@RequestBody Producer producer) {
        return producerService.save(producer);
    }


    @PostMapping("/{producerName}/models/{modelName}")
    @Operation(summary = "Create a new producer with names")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producer created successfully",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ProducerDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(mediaType = "application/json"))})
    @ResponseStatus(HttpStatus.CREATED)
    public ProducerDTO createNewProducerWithNames(@PathVariable String producerName,
                                                  @PathVariable String modelName) {
        Producer newProducer = Producer.builder()
            .producerName(producerName)
            .modelName(modelName)
            .build();
        return producerService.save(newProducer);
    }

    @PutMapping("/{producerId}")
    @Operation(summary = "Update producer by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producer updated successfully",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ProducerDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Producer not found",
            content = @Content(mediaType = "application/json"))})
    public ProducerDTO updateProducer(@PathVariable Long producerId, @RequestBody Producer producer) {
        return producerService.updateById(producerId, producer);
    }

    @PutMapping("/{producerName}/models/{oldModelName}_{newModelName}")
    @Operation(summary = "Update model name by producer name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Model updated successfully",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ProducerDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Producer not found",
            content = @Content(mediaType = "application/json"))})
    public ProducerDTO updateModel(@PathVariable String producerName,
                                   @PathVariable String oldModelName,
                                   @PathVariable String newModelName) {

        return producerService.updateModel(producerName, oldModelName, newModelName);
    }

    @DeleteMapping("/{producerName}/models/{modelName}")
    @Operation(summary = "Delete model by producer name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Model deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Producer or model not found",
            content = @Content(mediaType = "application/json"))})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModel(@PathVariable String producerName,
                            @PathVariable String modelName) {

        producerService.deleteModel(producerName, modelName);
    }

    @DeleteMapping("/{producerId}")
    @Operation(summary = "Delete producer by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producer deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Producer not found",
            content = @Content(mediaType = "application/json"))})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProducer(@PathVariable Long producerId) {
        producerService.deleteById(producerId);
    }
}
