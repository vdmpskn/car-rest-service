package ua.foxminded.carrest.controller;

import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ua.foxminded.carrest.dao.dto.ProducerDTO;
import ua.foxminded.carrest.dao.model.Producer;
import ua.foxminded.carrest.service.ProducerService;

@WebMvcTest(ProducerController.class)
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProducerService producerService;

    @InjectMocks
    private ProducerController producerController;

    @Test
    void shouldGetAllProducers() throws Exception {
        when(producerService.findAllPaged(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/producers"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldListOfModels() throws Exception {
        when(producerService.getAllModelsPaged(Mockito.anyString(), Mockito.any(Pageable.class)))
            .thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/producers/Test/models/"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetProducerById() throws Exception {
        when(producerService.findById(Mockito.anyLong())).thenReturn(new ProducerDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/producers/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldCreateProducer() throws Exception {
        when(producerService.save(Mockito.any(Producer.class))).thenReturn(new ProducerDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/producers")
                .content("{}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldCreateNewProducerWithNames() throws Exception {
        when(producerService.save(Mockito.any(Producer.class))).thenReturn(new ProducerDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/producers/Test/models/Test")
                .content("{}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateProducer() throws Exception {
        when(producerService.updateById(Mockito.anyLong(), Mockito.any(Producer.class))).thenReturn(new ProducerDTO());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/producers/1")
                .content("{}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateModel() throws Exception {
        when(producerService.updateModel(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
            .thenReturn(new ProducerDTO());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/producers/Test/models/OldModel_NewModel"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/producers/Test/models/Test"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteProducer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/producers/1"))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    void shouldNotGetProducerById_InvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/producers/invalidId"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldNotCreateProducer_MissingRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/producers"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldNotUpdateProducer_InvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/producers/invalidId")
                .content("{}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void shouldNotDeleteProducer_InvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/producers/invalidId"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
