package ua.foxminded.carrest.custom.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import ua.foxminded.carrest.dao.dto.ProducerDTO;

@Data
@Builder
public class ProducerSearchResponse {
    private List<ProducerDTO> producers;

    private int currentPage;

    private int pageSize;

    private Long totalElements;

    private int totalPages;
}
