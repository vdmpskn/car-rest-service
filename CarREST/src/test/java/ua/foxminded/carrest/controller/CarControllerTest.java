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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.foxminded.carrest.dao.dto.CarDTO;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.model.CarBodyType;
import ua.foxminded.carrest.service.CarService;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @Test
    void shouldGetAllCars() throws Exception {
        when(carService.findCarsPaged(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cars"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetCarById() throws Exception {
        when(carService.findById(Mockito.anyLong())).thenReturn(new CarDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cars/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldCreateCar() throws Exception {
        when(carService.save(any(CarDTO.class))).thenReturn(new CarDTO());

        Set<CarTypeDTO> carTypeDTOSet = new HashSet<>();
        CarTypeDTO carTypeDTO = new CarTypeDTO();
        carTypeDTO.setId(1L);
        carTypeDTO.setCarBodyType(CarBodyType.COUPE);
        carTypeDTOSet.add(carTypeDTO);

        String jsonPayload = new ObjectMapper().writeValueAsString(carTypeDTOSet);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cars/producer/Test/models/Test/years/2022")
                .content(jsonPayload)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void shouldUpdateCarYear() throws Exception {
        when(carService.updateCarYear(Mockito.anyLong(), Mockito.anyInt())).thenReturn(new CarDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cars/update/car/1/year/2022"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateCar() throws Exception {
        when(carService.updateCarById(Mockito.anyLong(), any(CarDTO.class))).thenReturn(new CarDTO());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cars/1")
                .content("{}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteCar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cars/producer/Test/models/Test/years/2022"))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

