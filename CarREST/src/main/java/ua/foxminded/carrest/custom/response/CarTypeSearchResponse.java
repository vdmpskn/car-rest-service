package ua.foxminded.carrest.custom.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;

@Data
@Builder
public class CarTypeSearchResponse {

    private List<CarTypeDTO> carDTOList;

    private int currentPage;

    private int pageSize;

    private Long totalElements;

    private int totalPages;
}
