package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoSpecificObjectives;
import ec.edu.espe.GrupoInvestigacion.service.IServiceSpecificObjectives;
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

@WebMvcTest(SpecificObjectivesController.class)
public class SpecificObjetivesControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/objectives";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceSpecificObjectives specificObjectivesService;

    @Test
    void getAllSpecificObjectives_ShouldReturnOkAndList() throws Exception {
        DtoSpecificObjectives dtoSpecificObjectives = new DtoSpecificObjectives();
        dtoSpecificObjectives.setObjetivo("Test Specific Objectives");
        List<DtoSpecificObjectives> dtoList = List.of(dtoSpecificObjectives);

        Mockito.when(specificObjectivesService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].objetivo").value("Test Specific Objectives"));
    }

    @Test
    void getSpecificObjectiveById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoSpecificObjectives dtoSpecificObjectives = new DtoSpecificObjectives();
        dtoSpecificObjectives.setIdObjetivo(id);
        dtoSpecificObjectives.setObjetivo("Test Specific Objectives");

        Mockito.when(specificObjectivesService.find(id)).thenReturn(dtoSpecificObjectives);
        mockMvc.perform(get(PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objetivo").value("Test Specific Objectives"));
    }

    @Test
    void createSpecificObjective_ShouldReturnCreatedAndId() throws Exception {
        DtoSpecificObjectives dtoSpecificObjectives = new DtoSpecificObjectives();
        dtoSpecificObjectives.setObjetivo("New Specific Objectives");

        Mockito.when(specificObjectivesService.save(Mockito.any(DtoSpecificObjectives.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"objetivo\": \"New Specific Objetives\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateSpecificObjectiveById_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoSpecificObjectives dtoSpecificObjectives = new DtoSpecificObjectives();
        dtoSpecificObjectives.setIdObjetivo(id);

        Mockito.when(specificObjectivesService.find(id)).thenReturn(dtoSpecificObjectives);
        mockMvc.perform(put(PATH + "/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"objetivo\": \"New Specific Objetives\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(specificObjectivesService, times(1)).update(Mockito.any(DtoSpecificObjectives.class));
    }
}
