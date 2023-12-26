package ua.foxminded.carrest.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProducerDTO {
    private Long id;

    private String producerName;

    private String modelName;

}
