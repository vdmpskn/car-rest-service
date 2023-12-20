package ua.foxminded.carrest.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProducerEntity {
    private Long id;

    private String producerName;

    private String modelName;

}
