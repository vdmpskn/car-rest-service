package ua.foxminded.carrest.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.carrest.dao.model.CarBodyType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarTypeEntity {

    private Long id;

    private CarBodyType carBodyType;

}
