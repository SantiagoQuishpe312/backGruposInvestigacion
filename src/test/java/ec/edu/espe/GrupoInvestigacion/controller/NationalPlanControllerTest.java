package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoMagazines;
import ec.edu.espe.GrupoInvestigacion.dto.DtoNationalPlan;
import ec.edu.espe.GrupoInvestigacion.service.IServiceNationalPlan;
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

@WebMvcTest(NationalPlanController.class)
public class NationalPlanControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/national-plans";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceNationalPlan nationalPlanService;

    @Test
    void getAllNationalPlans_ShouldReturnOkAndList() throws Exception {
        DtoNationalPlan dtoNationalPlan = new DtoNationalPlan();
        dtoNationalPlan.setDescripcion("Test National Plan");
        List<DtoNationalPlan> dtoList = List.of(dtoNationalPlan);

        Mockito.when(nationalPlanService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Test National Plan"));
    }

    @Test
    void getNationalPlanById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoNationalPlan dtoNationalPlan = new DtoNationalPlan();
        dtoNationalPlan.setIdPlanNacional(id);
        dtoNationalPlan.setDescripcion("Test National Plan");

        Mockito.when(nationalPlanService.find(id)).thenReturn(dtoNationalPlan);
        mockMvc.perform(get(PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Test National Plan"));
    }

    @Test
    void createNationalPlan_ShouldReturnCreatedAndId() throws Exception {
        DtoNationalPlan dtoNationalPlan = new DtoNationalPlan();
        dtoNationalPlan.setDescripcion("Test National Plan");

        Mockito.when(nationalPlanService.save(Mockito.any(DtoNationalPlan.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"descripcion\": \"New National Plan\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateNationalPlan_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoNationalPlan dtoNationalPlan = new DtoNationalPlan();
        dtoNationalPlan.setIdPlanNacional(id);

        Mockito.when(nationalPlanService.find(id)).thenReturn(dtoNationalPlan);
        mockMvc.perform(put(PATH + "/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"descripcion\": \"New National Plan\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(nationalPlanService, times(1)).update(Mockito.any(DtoNationalPlan.class));
    }

}
