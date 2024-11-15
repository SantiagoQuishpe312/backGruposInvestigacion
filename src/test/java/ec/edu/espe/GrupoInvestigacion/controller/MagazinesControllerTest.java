package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoMagazines;
import ec.edu.espe.GrupoInvestigacion.service.IServiceMagazines;
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

@WebMvcTest(MagazinesController.class)
public class MagazinesControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/magazines";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceMagazines magazinesService;

    @Test
    void getAllMagazines_ShouldReturnOkAndList() throws Exception {
        DtoMagazines dtoMagazines = new DtoMagazines();
        dtoMagazines.setTitulo("Test Magazine");
        List<DtoMagazines> dtoList = List.of(dtoMagazines);

        Mockito.when(magazinesService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Test Magazine"));
    }

    @Test
    void getMagazineById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoMagazines dtoMagazines = new DtoMagazines();
        dtoMagazines.setIdRevista(id);
        dtoMagazines.setTitulo("Test Magazine");

        Mockito.when(magazinesService.find(id)).thenReturn(dtoMagazines);
        mockMvc.perform(get(PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Test Magazine"));
    }

    @Test
    void createMagazine_ShouldReturnCreatedAndId() throws Exception {
        DtoMagazines dtoMagazines = new DtoMagazines();
        dtoMagazines.setTitulo("Test Magazine");

        Mockito.when(magazinesService.save(Mockito.any(DtoMagazines.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"titulo\": \"New Maganize\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateMagazine_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoMagazines dtoMagazines = new DtoMagazines();
        dtoMagazines.setIdRevista(id);

        Mockito.when(magazinesService.find(id)).thenReturn(dtoMagazines);

        mockMvc.perform(put(PATH + "/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"titulo\": \"New Maganize\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(magazinesService, times(1)).update(Mockito.any(DtoMagazines.class));
    }
}
