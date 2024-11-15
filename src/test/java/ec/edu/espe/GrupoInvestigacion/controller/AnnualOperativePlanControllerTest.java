package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoAnnualOperativePlan;
import ec.edu.espe.GrupoInvestigacion.service.IServiceAnnualOperativePlan;
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

@WebMvcTest(AnnualOperativePlanController.class)
public class AnnualOperativePlanControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/annualOperativePlan/";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceAnnualOperativePlan annualOperativePlanService;

    @Test
    void getAllAnnualOperativePlan_ShouldReturnOkAndList() throws Exception {
        DtoAnnualOperativePlan dtoAnnualOperativePlan = new DtoAnnualOperativePlan();
        dtoAnnualOperativePlan.setObjetivoGeneral("Test Annual Operative Plan");
        List<DtoAnnualOperativePlan> dtoList = List.of(dtoAnnualOperativePlan);

        Mockito.when(annualOperativePlanService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].objetivoGeneral").value("Test Annual Operative Plan"));
    }

    @Test
    void getAnnualOperativePlanById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoAnnualOperativePlan dtoAnnualOperativePlan = new DtoAnnualOperativePlan();
        dtoAnnualOperativePlan.setIdAnnualOperativePlan(id);
        dtoAnnualOperativePlan.setObjetivoGeneral("Test Annual Operative Plan");

        Mockito.when(annualOperativePlanService.findById(id)).thenReturn(dtoAnnualOperativePlan);

        mockMvc.perform(get(PATH+"/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objetivoGeneral").value("Test Annual Operative Plan"));
    }

    @Test
    void createAnnualOperativePlan_ShouldReturnCreatedAndId() throws Exception {
        DtoAnnualOperativePlan dtoAnnualOperativePlan = new DtoAnnualOperativePlan();
        dtoAnnualOperativePlan.setObjetivoGeneral("New Annual Operative Plan");

        Mockito.when(annualOperativePlanService.save(Mockito.any(DtoAnnualOperativePlan.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/created")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"objetivoGeneral\": \"New Annual Operative Plan\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateAnnualOperativePlan_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoAnnualOperativePlan dtoAnnualOperativePlan = new DtoAnnualOperativePlan();
        dtoAnnualOperativePlan.setIdAnnualOperativePlan(id);

        Mockito.when(annualOperativePlanService.findById(id)).thenReturn(dtoAnnualOperativePlan);

        mockMvc.perform(put(PATH+"/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"objetivoGeneral\": \"New Annual Operative\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(annualOperativePlanService, times(1)).update(Mockito.any(DtoAnnualOperativePlan.class));
    }
}
