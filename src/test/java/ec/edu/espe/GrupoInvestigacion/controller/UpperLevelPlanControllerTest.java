package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoUpperLevelPlan;
import ec.edu.espe.GrupoInvestigacion.service.IServiceUpperLevelPlan;
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

@WebMvcTest(UpperLevelPlanController.class)
public class UpperLevelPlanControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/upper-level-plans";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceUpperLevelPlan upperLevelPlanService;

    @Test
    void getAllUpperLevelPlan_ShouldReturnOkAndList() throws Exception {
        DtoUpperLevelPlan dtoUpperLevelPlan = new DtoUpperLevelPlan();
        dtoUpperLevelPlan.setNombre("Test Upper Level Plan");
        List<DtoUpperLevelPlan> dtoList = List.of(dtoUpperLevelPlan);

        Mockito.when(upperLevelPlanService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Test Upper Level Plan"));
    }

    @Test
    void getUpperLevelPlanById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoUpperLevelPlan dtoUpperLevelPlan = new DtoUpperLevelPlan();
        dtoUpperLevelPlan.setIdPlanNivelSuperior(id);
        dtoUpperLevelPlan.setNombre("Test Upper Level Plan");

        Mockito.when(upperLevelPlanService.find(id)).thenReturn(dtoUpperLevelPlan);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test Upper Level Plan"));
    }

    @Test
    void createUpperLevelPlan_ShouldReturnCreatedAndId() throws Exception {
        DtoUpperLevelPlan dtoUpperLevelPlan = new DtoUpperLevelPlan();
        dtoUpperLevelPlan.setNombre("Test Upper Level Plan");

        Mockito.when(upperLevelPlanService.save(Mockito.any(DtoUpperLevelPlan.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombre\": \"New Upper Level Plan\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateUpperLevelPlan_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoUpperLevelPlan dtoUpperLevelPlan = new DtoUpperLevelPlan();
        dtoUpperLevelPlan.setIdPlanNivelSuperior(id);

        Mockito.when(upperLevelPlanService.find(id)).thenReturn(dtoUpperLevelPlan);
        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombre\": \"Updated Upper Level Plan\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(upperLevelPlanService, times(1)).update(Mockito.any(DtoUpperLevelPlan.class));
    }
}
