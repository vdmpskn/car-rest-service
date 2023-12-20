package ua.foxminded.carrest.dao.entity;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarEntity {

    private Long id;

    private int year;

    private ProducerEntity producerEntity;

    private Set<CarTypeEntity> carTypeEntities;

}
