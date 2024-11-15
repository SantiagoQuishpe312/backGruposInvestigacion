package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoProgressAchiev;
import ec.edu.espe.GrupoInvestigacion.service.IServiceProgressAch;
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

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(ProgressAchController.class)
public class ProgresAchControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/progress-ach";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceProgressAch progressAchService;

    @Test
    void getAllProgressAch_ShouldReturnOkAndList() throws Exception {
        DtoProgressAchiev dtoProgressAchiev = new DtoProgressAchiev();
        dtoProgressAchiev.setDescripcion("Test Progress Achiev");
        List<DtoProgressAchiev> dtoProgressAchievList = List.of(dtoProgressAchiev);

        Mockito.when(progressAchService.findAll()).thenReturn(dtoProgressAchievList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Test Progress Achiev"));
    }

    @Test
    void getProgressAchById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoProgressAchiev dtoProgressAchiev = new DtoProgressAchiev();
        dtoProgressAchiev.setIdLogro(id);
        dtoProgressAchiev.setDescripcion("Test Progress Achiev");

        Mockito.when(progressAchService.find(id)).thenReturn(dtoProgressAchiev);
        mockMvc.perform(get(PATH+"/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Test Progress Achiev"));
    }

    @Test
    void createProgressAch_ShouldReturnCreatedAndId() throws Exception {
        DtoProgressAchiev dtoProgressAchiev = new DtoProgressAchiev();
        dtoProgressAchiev.setDescripcion("New Progress Achiev");

        Mockito.when(progressAchService.save(Mockito.any(DtoProgressAchiev.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"descripcion\": \"New Progress Achiev\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateProgressAchById_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoProgressAchiev dtoProgressAchiev = new DtoProgressAchiev();
        dtoProgressAchiev.setIdLogro(id);

        Mockito.when(progressAchService.find(id)).thenReturn(dtoProgressAchiev);
        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"descripcion\": \"New Progress Achiev\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(progressAchService, times(1)).update(Mockito.any(DtoProgressAchiev.class));
    }

}
