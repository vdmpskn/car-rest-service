package ua.foxminded.carrest.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.entity.ProducerEntity;
import ua.foxminded.carrest.dao.model.Producer;

@Component
@RequiredArgsConstructor
public class ProducerConverter {

    private final ModelMapper modelMapper;

    public ProducerEntity convertToEntity(Producer producer) {
        return modelMapper.map(producer, ProducerEntity.class);
    }

    public Producer convertToModel(ProducerEntity producerEntity) {
        return modelMapper.map(producerEntity, Producer.class);
    }

}
