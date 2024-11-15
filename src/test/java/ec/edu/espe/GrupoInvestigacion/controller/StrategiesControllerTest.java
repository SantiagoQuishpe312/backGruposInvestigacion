package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoStrategies;
import ec.edu.espe.GrupoInvestigacion.service.IServiceStrategies;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.PRIVATE_PATH;
import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.V1_API_VERSION;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.List;

@WebMvcTest(StrategiesController.class)
public class StrategiesControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/strategies";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceStrategies strategiesService;

    @Test
    void getAllStrategies_ShouldReturnOkAndList() throws Exception {
        DtoStrategies dtoStrategies = new DtoStrategies();
        dtoStrategies.setEstrategia("Test strategies");
        List<DtoStrategies> dtoList = List.of(dtoStrategies);

        Mockito.when(strategiesService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estrategia").value("Test strategies"));
    }

    @Test
    void getStrategiesById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoStrategies dtoStrategies = new DtoStrategies();
        dtoStrategies.setIdEstrategia(id);
        dtoStrategies.setEstrategia("Test strategies");

        Mockito.when(strategiesService.find(id)).thenReturn(dtoStrategies);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estrategia").value("Test strategies"));
    }

    @Test
    void getStrategiesByObj_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoStrategies dtoStrategies = new DtoStrategies();
        dtoStrategies.setIdObjetivo(id);
        dtoStrategies.setEstrategia("Test strategies");
        List<DtoStrategies> dtoList = List.of(dtoStrategies);

        Mockito.when(strategiesService.findByObj(id)).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/byObj/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estrategia").value("Test strategies"));
    }

    @Test
    void createStrategies_ShouldReturnCreatedAndId() throws Exception {
        DtoStrategies dtoStrategies = new DtoStrategies();
        dtoStrategies.setEstrategia("New strategies");

        Mockito.when(strategiesService.save(Mockito.any(DtoStrategies.class))).thenReturn(1l);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"estrategia\": \"New strategies\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateStrategies_ShouldReturnUpdatedDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoStrategies dtoStrategies = new DtoStrategies();
        dtoStrategies.setIdEstrategia(id);

        Mockito.when(strategiesService.find(id)).thenReturn(dtoStrategies);
        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"estrategia\": \"Updated strategies\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(strategiesService, times(1)).update(Mockito.any(DtoStrategies.class));
    }
}
