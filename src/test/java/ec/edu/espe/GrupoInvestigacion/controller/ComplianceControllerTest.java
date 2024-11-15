package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoCompliance;
import ec.edu.espe.GrupoInvestigacion.service.IServiceCompliance;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ComplianceController.class)
public class ComplianceControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/compliance";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceCompliance complianceService;

    @Test
    void getComplianceByIdActReport_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoCompliance dtoCompliance = new DtoCompliance();
        dtoCompliance.setIdReporteActividades(id);
        dtoCompliance.setVerificable("Test Compliance");
        List<DtoCompliance> dtoList = List.of(dtoCompliance);

        Mockito.when(complianceService.findByActReport(id)).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/activityReport/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].verificable").value("Test Compliance"));
    }

    @Test
    void getComplianceByIdObj_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoCompliance dtoCompliance = new DtoCompliance();
        dtoCompliance.setIdObjEspecifico(id);
        dtoCompliance.setVerificable("Test Compliance");
        List<DtoCompliance> dtoList = List.of(dtoCompliance);

        Mockito.when(complianceService.findByObj(id)).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/specificObj/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].verificable").value("Test Compliance"));
    }

    @Test
    void createCompliance_ShouldReturnCreatedAndId() throws Exception {
        DtoCompliance dtoCompliance = new DtoCompliance();
        dtoCompliance.setVerificable("New Compliance");

        Mockito.when(complianceService.save(Mockito.any(DtoCompliance.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"verificable\": \"New Compliance\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }
}
