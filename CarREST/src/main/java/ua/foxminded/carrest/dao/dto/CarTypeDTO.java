package ua.foxminded.carrest.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.carrest.dao.model.CarBodyType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarTypeDTO {

    private Long id;

    private CarBodyType carBodyType;

}
