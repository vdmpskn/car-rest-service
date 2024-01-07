package ua.foxminded.carrest.custom.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import ua.foxminded.carrest.dao.dto.CarDTO;

@Data
@Builder
public class CarSearchResponse {
    private List<CarDTO> carDTOList;

    private int currentPage;

    private int pageSize;

    private Long totalElements;

    private int totalPages;
}
