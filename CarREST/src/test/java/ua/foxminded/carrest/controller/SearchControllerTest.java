package ua.foxminded.carrest.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

import ua.foxminded.carrest.service.CarService;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @InjectMocks
    private SearchController searchController;

    @Test
    void testSearchByProducerName() throws Exception {
        when(carService.findCarsByProducerName(Mockito.anyString(), Mockito.any(Pageable.class)))
            .thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/search/producer/Test"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testSearchByModelName() throws Exception {
        when(carService.findCarsByModelName(Mockito.anyString(), Mockito.any(Pageable.class)))
            .thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/search/model/Test"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testSearchByYearRange() throws Exception {
        when(carService.findCarsByYearRange(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Pageable.class)))
            .thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/search/year/2000_2022"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
