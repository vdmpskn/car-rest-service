package ua.foxminded.carrest.custom.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ModelSearchResponse {
    private List<String> models;

    private int currentPage;

    private int pageSize;

    private Long totalElements;

    private int totalPages;
}
