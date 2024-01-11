package ua.foxminded.carrest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import ua.foxminded.carrest.config.SecurityConfig;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.model.CarBodyType;
import ua.foxminded.carrest.dao.model.CarType;
import ua.foxminded.carrest.service.CarTypeService;

@WebMvcTest(CarTypeController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class CarTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarTypeService carTypeService;

    @InjectMocks
    private CarTypeController carTypeController;

    @Test
    void shouldGetAllCarTypes() throws Exception {
        CarTypeDTO carTypeDTO1 = CarTypeDTO.builder()
            .id(1L)
            .carBodyType(CarBodyType.COUPE).build();
        CarTypeDTO carTypeDTO2 = CarTypeDTO.builder()
            .id(2L)
            .carBodyType(CarBodyType.HATCHBACK).build();
        List<CarTypeDTO> expectedCarTypeDTOs = List.of(carTypeDTO1, carTypeDTO2);
        Page<CarTypeDTO> mockPage = new PageImpl<>(expectedCarTypeDTOs);
        when(carTypeService.carTypeList(any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/api/v1/car-types"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.carDTOList").isArray())
            .andExpect(jsonPath("$.carDTOList", hasSize(expectedCarTypeDTOs.size())))
            .andExpect(jsonPath("$.currentPage").value(0))
            .andExpect(jsonPath("$.pageSize").value(2))
            .andExpect(jsonPath("$.totalElements").value(expectedCarTypeDTOs.size()))
            .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void shouldGetCarTypeById() throws Exception {
        when(carTypeService.getCarTypeById(Mockito.anyLong())).thenReturn(new CarTypeDTO());

        mockMvc.perform(get("/api/v1/car-types/1"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCarById() throws Exception {
        when(carTypeService.updateById(Mockito.anyLong(), any(CarType.class))).thenReturn(new CarTypeDTO());

        mockMvc.perform(put("/api/v1/car-types/1")
                .content("{}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCarType() throws Exception {
        mockMvc.perform(delete("/api/v1/car-types/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenNoCarTypeExists() throws Exception {
        when(carTypeService.carTypeList(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(get("/api/v1/car-types"))
            .andExpect(status().isNotFound());

    }

    @Test
    void shouldGetCarTypeById_CarTypeNotFound() throws Exception {
        when(carTypeService.getCarTypeById(Mockito.anyLong())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/v1/car-types/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateCarById_CarTypeNotFound() throws Exception {
        when(carTypeService.updateById(Mockito.anyLong(), Mockito.any(CarType.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(put("/api/v1/car-types/1")
                .content("{}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCarType_CarTypeNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(carTypeService).deleteById(Mockito.anyLong());

        mockMvc.perform(delete("/api/v1/car-types/1"))
            .andExpect(status().isNotFound());
    }

}
