package ua.foxminded.carrest.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.dao.dto.ProducerDTO;
import ua.foxminded.carrest.dao.model.Producer;

@Component
@RequiredArgsConstructor
public class ProducerConverter {

    private final ModelMapper modelMapper;

    public ProducerDTO convertToDTO(Producer producer) {
        return modelMapper.map(producer, ProducerDTO.class);
    }

    public Producer convertToModel(ProducerDTO producerDTO) {
        return modelMapper.map(producerDTO, Producer.class);
    }

}
