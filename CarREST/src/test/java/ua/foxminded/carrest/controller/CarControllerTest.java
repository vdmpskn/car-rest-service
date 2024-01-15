package ua.foxminded.carrest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.foxminded.carrest.config.SecurityConfig;
import ua.foxminded.carrest.custom.response.AuthRequest;
import ua.foxminded.carrest.dao.dto.CarDTO;
import ua.foxminded.carrest.dao.dto.CarTypeDTO;
import ua.foxminded.carrest.dao.dto.ProducerDTO;
import ua.foxminded.carrest.dao.model.CarBodyType;
import ua.foxminded.carrest.service.CarService;

@WebMvcTest(CarController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @Test
    void shouldGetAllCars() throws Exception {
        Set<CarTypeDTO> carTypes = new HashSet<>();
        carTypes.add(new CarTypeDTO(1L,CarBodyType.VAN_MINIVAN));
        CarDTO car1 = new CarDTO(1L, 2023, ProducerDTO.builder()
            .id(1L)
            .modelName("Camry")
            .producerName("Toyota")
            .build(), carTypes);
        CarDTO car2 = new CarDTO(1L, 2023, ProducerDTO.builder()
            .id(1L)
            .modelName("Camry")
            .producerName("Toyota")
            .build(), carTypes);
        List<CarDTO> expectedCarDTOs = List.of(car1, car2);
        Page<CarDTO> mockPage = new PageImpl<>(expectedCarDTOs);
        when(carService.findCarsPaged(any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cars"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.carDTOList").isArray())
            .andExpect(jsonPath("$.carDTOList", hasSize(expectedCarDTOs.size())))
            .andExpect(jsonPath("$.currentPage").value(0))
            .andExpect(jsonPath("$.pageSize").value(2))
            .andExpect(jsonPath("$.totalElements").value(expectedCarDTOs.size()))
            .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void shouldGetCarById() throws Exception {
        when(carService.findById(Mockito.anyLong())).thenReturn(new CarDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cars/1"))
            .andExpect(status().isOk());
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
            .andExpect(status().isOk());
    }


    @Test
    void shouldUpdateCarYear() throws Exception {
        when(carService.updateCarYear(Mockito.anyLong(), Mockito.anyInt())).thenReturn(new CarDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cars/update/car/1/year/2022"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCar() throws Exception {
        when(carService.updateCarById(Mockito.anyLong(), any(CarDTO.class))).thenReturn(new CarDTO());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cars/1")
                .content("{}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cars/producer/Test/models/Test/years/2022"))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenNoCarsExist() throws Exception {
        when(carService.findCarsPaged(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cars"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenCarIdDoesNotExist() throws Exception {
        when(carService.findById(Mockito.anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cars/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cars/producer/Test/models/Test/years/2022")
                .content("{}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenCarForYearUpdateDoesNotExist() throws Exception {
        AuthRequest authRequest = new AuthRequest("testusr@gmail.com", "123321testT");
        when(carService.updateCarYear(Mockito.anyLong(), Mockito.anyInt())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cars/update/car/1/year/2022"))
            .andExpect(status().isNotFound());
    }
}

