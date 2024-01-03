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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.service.CarTypeService;

@WebMvcTest(CarTypeController.class)
class CarTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarTypeService carTypeService;

    @InjectMocks
    private CarTypeController carTypeController;

    @Test
    void shouldGetAllCarTypes() throws Exception {
        when(carTypeService.carTypeList(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/car-types"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetCarTypeById() throws Exception {
        when(carTypeService.getCarTypeById(Mockito.anyLong())).thenReturn(new CarTypeDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/car-types/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateCarById() throws Exception {
        when(carTypeService.updateById(Mockito.anyLong(), Mockito.any(CarType.class))).thenReturn(new CarTypeDTO());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/car-types/1")
                .content("{}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteCarType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/car-types/1"))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
