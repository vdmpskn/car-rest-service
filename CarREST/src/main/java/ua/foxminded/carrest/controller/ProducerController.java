package ua.foxminded.carrest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<List<Producer>> getAllProducers(){
        List<Producer> producerList = producerService.findAll();
        return new ResponseEntity<>(producerList, HttpStatus.OK);
    }

    @GetMapping("/{producerId}")
    public ResponseEntity<Producer> getProducerById(@PathVariable Long producerId){
        Producer producer = producerService.findById(producerId);

        return new ResponseEntity<>(producer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Producer> createProducer(@RequestBody Producer producer){
        Producer savedProducer = producerService.save(producer);

        return new ResponseEntity<>(savedProducer, HttpStatus.OK);
    }

    @PutMapping("/{producerId}")
    public ResponseEntity<Producer> updateProducer(@PathVariable Long producerId, @RequestBody Producer producer){
        Producer updatedProducer = producerService.updateById(producerId, producer);
        return new ResponseEntity<>(updatedProducer, HttpStatus.OK);
    }

    @DeleteMapping("/{producerId}")
    public ResponseEntity<Void> deleteProducer(@PathVariable Long producerId){
        producerService.deleteById(producerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
