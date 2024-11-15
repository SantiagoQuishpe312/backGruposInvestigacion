package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoRelevanceReport;
import ec.edu.espe.GrupoInvestigacion.service.IServiceRelevanceReport;
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

@WebMvcTest(RelevanceReportController.class)
public class RelevanceReportControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/relevance-report";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceRelevanceReport relevanceReportService;

    @Test
    void getAllRelevanceReports_ShouldReturnOkAndList() throws Exception {
        DtoRelevanceReport dtoRelevanceReport = new DtoRelevanceReport();
        dtoRelevanceReport.setNumeroMemo("Test Relevance Report");
        List<DtoRelevanceReport> dtoList = List.of(dtoRelevanceReport);

        Mockito.when(relevanceReportService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroMemo").value("Test Relevance Report"));
    }

    @Test
    void getRelevanceReportById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoRelevanceReport dtoRelevanceReport = new DtoRelevanceReport();
        dtoRelevanceReport.setIdInformePertinencia(id);
        dtoRelevanceReport.setNumeroMemo("Test Relevance Report");

        Mockito.when(relevanceReportService.find(id)).thenReturn(dtoRelevanceReport);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroMemo").value("Test Relevance Report"));
    }

    @Test
    void createRelevanceReport_ShouldReturnCreatedAndId() throws Exception {
        DtoRelevanceReport dtoRelevanceReport = new DtoRelevanceReport();
        dtoRelevanceReport.setNumeroMemo("New Relevance Report");

        Mockito.when(relevanceReportService.save(Mockito.any(DtoRelevanceReport.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"numeroMemo\": \"New Relevance Report\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateRelevanceReport_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoRelevanceReport dtoRelevanceReport = new DtoRelevanceReport();
        dtoRelevanceReport.setIdInformePertinencia(id);

        Mockito.when(relevanceReportService.find(id)).thenReturn(dtoRelevanceReport);
        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"numeroMemo\": \"New Relevance Report\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(relevanceReportService, times(1)).update(Mockito.any(DtoRelevanceReport.class));
    }
}
