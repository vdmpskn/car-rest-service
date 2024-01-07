package ua.foxminded.carrest.dao.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

    private Long id;

    private int year;

    private ProducerDTO producer;

    private Set<CarTypeDTO> carType;

}
