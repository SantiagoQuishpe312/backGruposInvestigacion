package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDevelopmentPlan;
import ec.edu.espe.GrupoInvestigacion.service.IServiceDevelopmentPlan;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(DevelopmentPlanController.class)
public class DevelopmentPlanControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH + "/development-plan";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceDevelopmentPlan developmentPlanService;

    @Test
    void getAllDevelopmentPlan_ShouldReturnOkAndList() throws Exception {
        DtoDevelopmentPlan dtoDevelopmentPlan = new DtoDevelopmentPlan();
        dtoDevelopmentPlan.setEstado('1');
        List<DtoDevelopmentPlan> dtoList = List.of(dtoDevelopmentPlan);

        Mockito.when(developmentPlanService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("1"));
    }

    @Test
    void getDevelopmentPlanById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoDevelopmentPlan dtoDevelopmentPlan = new DtoDevelopmentPlan();
        dtoDevelopmentPlan.setIdPlanDesarrollo(id);
        dtoDevelopmentPlan.setEstado('1');

        Mockito.when(developmentPlanService.find(id)).thenReturn(dtoDevelopmentPlan);

        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("1"));
    }

    @Test
    void getDevelopmentPlanByGroupC_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoDevelopmentPlan dtoDevelopmentPlan = new DtoDevelopmentPlan();
        dtoDevelopmentPlan.setIdGrupoInv(id);
        dtoDevelopmentPlan.setEstado('1');
        List<DtoDevelopmentPlan> dtoList = List.of(dtoDevelopmentPlan);

        Mockito.when(developmentPlanService.findGroupC(id)).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/groupC/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("1"));
    }

    @Test
    void getDevelpmentPlanByGroupType_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        Character tipo = '1';
        DtoDevelopmentPlan dtoDevelopmentPlan = new DtoDevelopmentPlan();
        dtoDevelopmentPlan.setIdGrupoInv(id);
        dtoDevelopmentPlan.setTipo(tipo);
        dtoDevelopmentPlan.setEstado('1');
        List<DtoDevelopmentPlan> dtoList = List.of(dtoDevelopmentPlan);

        Mockito.when(developmentPlanService.findGroupType(id, tipo)).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/group/{id}/Type/{tipo}",id,tipo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("1"));
    }

    @Test
    void createDevelopmentPlan_ShouldReturnCreatedAndId() throws Exception {
        DtoDevelopmentPlan dtoDevelopmentPlan = new DtoDevelopmentPlan();
        dtoDevelopmentPlan.setEstado('1');

        Mockito.when(developmentPlanService.create(Mockito.any(DtoDevelopmentPlan.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"estado\": \"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateDevelopmentPlan_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoDevelopmentPlan dtoDevelopmentPlan = new DtoDevelopmentPlan();
        dtoDevelopmentPlan.setIdPlanDesarrollo(id);

        Mockito.when(developmentPlanService.find(id)).thenReturn(dtoDevelopmentPlan);

        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"estado\": \"1\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(developmentPlanService, times(1)).update(Mockito.any(DtoDevelopmentPlan.class));
    }
}
