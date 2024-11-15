package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDegreeTesis;
import ec.edu.espe.GrupoInvestigacion.service.IServiceDegreeTesis;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.PRIVATE_PATH;
import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.V1_API_VERSION;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(DegreeTesisController.class)
public class DegreeTesisControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/degree-teses";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceDegreeTesis degreeTesisService;

    @Test
    void getAllDegreeTesis_ShouldReturnOkAndList() throws Exception {
        DtoDegreeTesis dtoDegreeTesis = new DtoDegreeTesis();
        dtoDegreeTesis.setNombre("Test Tesis");
        List<DtoDegreeTesis> dtoList = List.of(dtoDegreeTesis);

        Mockito.when(degreeTesisService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Test Tesis"));
    }

    @Test
    void getDegreeTesisById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoDegreeTesis dtoDegreeTesis = new DtoDegreeTesis();
        dtoDegreeTesis.setIdTesis(id);
        dtoDegreeTesis.setNombre("Test Tesis");

        Mockito.when(degreeTesisService.find(id)).thenReturn(dtoDegreeTesis);

        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test Tesis"));
    }

    @Test
    void createDegreeTesis_ShouldReturnCreatedAndId() throws Exception {
        DtoDegreeTesis dtoDegreeTesis = new DtoDegreeTesis();
        dtoDegreeTesis.setNombre("Test Tesis");

        Mockito.when(degreeTesisService.save(Mockito.any(DtoDegreeTesis.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombre\": \"New Tesis\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateDegreeTesis_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoDegreeTesis dtoDegreeTesis = new DtoDegreeTesis();
        dtoDegreeTesis.setIdTesis(id);

        Mockito.when(degreeTesisService.find(id)).thenReturn(dtoDegreeTesis);

        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombre\": \"New Tesis\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(degreeTesisService, times(1)).update(Mockito.any(DtoDegreeTesis.class));
    }

}
