package ec.edu.espe.GrupoInvestigacion.controller;


import ec.edu.espe.GrupoInvestigacion.dto.DtoActivityReport;
import ec.edu.espe.GrupoInvestigacion.service.IServiceActivityReport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.PRIVATE_PATH;
import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.V1_API_VERSION;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//ActivityReportControllerTest.java
@WebMvcTest(ActivityReportController.class)
public class ActivityReportControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/actReport";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceActivityReport activityReportService;

    @Test
    void getAllActivityReports_ShouldReturnOkAndList() throws Exception {
        DtoActivityReport dtoActivityReport = new DtoActivityReport();
        dtoActivityReport.setEstado('1');
        List<DtoActivityReport> dtoList = List.of(dtoActivityReport);

        Mockito.when(activityReportService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("1"));
    }

    @Test
    void getActivityReportById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoActivityReport dtoActivityReport = new DtoActivityReport();
        dtoActivityReport.setIdInformeActividades(id);
        dtoActivityReport.setEstado('1');

        Mockito.when(activityReportService.find(id)).thenReturn(dtoActivityReport);

        mockMvc.perform(get(PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("1"));
    }

    @Test
    void getActivityReportByState_ShouldReturnOkAndList() throws Exception {
        Character state= '1';
        DtoActivityReport dtoActivityReport = new DtoActivityReport();
        dtoActivityReport.setEstado('1');
        List<DtoActivityReport> dtoList = List.of(dtoActivityReport);

        Mockito.when(activityReportService.findByState(state)).thenReturn(dtoList);

        mockMvc.perform(get(PATH + "/state/{state}", state))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("1"));

    }

    @Test
    void createActivityReport_ShouldReturnCreatedAndId() throws Exception {
        DtoActivityReport dtoActivityReport = new DtoActivityReport();
        dtoActivityReport.setEstado('1');

        Mockito.when(activityReportService.save(Mockito.any(DtoActivityReport.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"estado\": \"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateActivityReport_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoActivityReport dtoActivityReport = new DtoActivityReport();
        dtoActivityReport.setIdInformeActividades(id);

        Mockito.when(activityReportService.find(id)).thenReturn(dtoActivityReport);

        mockMvc.perform(put(PATH + "/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"estado\": \"1\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(activityReportService, times(1)).update(Mockito.any(DtoActivityReport.class));

    }

}
