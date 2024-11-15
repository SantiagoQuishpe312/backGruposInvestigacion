package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoAssetsReport;
import ec.edu.espe.GrupoInvestigacion.service.IServiceAssetsReport;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(AssetsReportController.class)
public class AssetsReportControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/assestsReport/";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceAssetsReport assetsReportService;

    @Test
    void getAllAssetsReport_ShouldReturnOkAndList() throws Exception {
        DtoAssetsReport dtoAssetsReport = new DtoAssetsReport();
        dtoAssetsReport.setObjetivoReporte("Test Assets Report");
        List<DtoAssetsReport> dtoList = List.of(dtoAssetsReport);

        Mockito.when(assetsReportService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+ "/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].objetivoReporte").value("Test Assets Report"));
    }

    @Test
    void getAssetsReportById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoAssetsReport dtoAssetsReport = new DtoAssetsReport();
        dtoAssetsReport.setIdReporteActivos(id);
        dtoAssetsReport.setObjetivoReporte("Test Assets Report");

        Mockito.when(assetsReportService.find(id)).thenReturn(dtoAssetsReport);

        mockMvc.perform(get(PATH +"{id}" ,id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objetivoReporte").value("Test Assets Report"));
    }

    @Test
    void createAssetsReport_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        DtoAssetsReport dtoAssetsReport = new DtoAssetsReport();
        dtoAssetsReport.setObjetivoReporte("Test Assets Report");

        Mockito.when(assetsReportService.save(Mockito.any(DtoAssetsReport.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"objetivoReporte\": \"New Assets Report\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateAssetsReport_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoAssetsReport dtoAssetsReport = new DtoAssetsReport();
        dtoAssetsReport.setIdReporteActivos(id);

        Mockito.when(assetsReportService.find(id)).thenReturn(dtoAssetsReport);

        mockMvc.perform(put(PATH +"/update/{id}" ,id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"objetivoReporte\": \"Updated Assets Report\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(assetsReportService,times(1)).update(Mockito.any(DtoAssetsReport.class));
    }



}
