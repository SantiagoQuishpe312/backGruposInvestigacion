package ec.edu.espe.GrupoInvestigacion.controller;


import ec.edu.espe.GrupoInvestigacion.dto.DtoAnnualControl;
import ec.edu.espe.GrupoInvestigacion.service.IServiceAnnualControl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.List;
import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.PRIVATE_PATH;
import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.V1_API_VERSION;

@WebMvcTest(AnnualControlController.class)
public class AnnualControlControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH +"/annualControl";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceAnnualControl annualControlService;

    @Test
    void getAnnualControlByPanel_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoAnnualControl dtoAnnualControl = new DtoAnnualControl();
        dtoAnnualControl.setIdPanelControl(id);
        dtoAnnualControl.setCertificado("Test Annual Control");
        List<DtoAnnualControl> dtoList = List.of(dtoAnnualControl);

        Mockito.when(annualControlService.findByPanel(id)).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/ControlPanel/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].certificado").value("Test Annual Control"));
    }

    @Test
    void getAnnualControlByPlan_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoAnnualControl dtoAnnualControl = new DtoAnnualControl();
        dtoAnnualControl.setIdPlanAnual(id);
        dtoAnnualControl.setCertificado("Test Annual Control");
        List<DtoAnnualControl> dtoList = List.of(dtoAnnualControl);

        Mockito.when(annualControlService.findByPlan(id)).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/AnnualPlan/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].certificado").value("Test Annual Control"));
    }

    /*
    @Test
    void getAnnualControlByIds_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        Long id2 = 2L;
        DtoAnnualControl dtoAnnualControl = new DtoAnnualControl();
        dtoAnnualControl.setIdPanelControl(id);
        dtoAnnualControl.setIdPlanAnual(id2);
        dtoAnnualControl.setCertificado("Test Annual Control");

        Mockito.when(annualControlService.findByIds(id, id2)).thenReturn(dtoAnnualControl);

        mockMvc.perform(get(PATH+"/{id}/{id2}", id, id2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.certificado").value("Test Annual Control"))
                .andExpect(jsonPath("$.idPanelControl").value(id));
    }*/

    @Test
    void createAnnualControl_ShouldReturnCreatedAndId() throws Exception {
        DtoAnnualControl dtoAnnualControl = new DtoAnnualControl();
        dtoAnnualControl.setCertificado("New Annual Control");

        Mockito.when(annualControlService.save(Mockito.any(DtoAnnualControl.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"certificado\": \"New Annual Control\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }
}
