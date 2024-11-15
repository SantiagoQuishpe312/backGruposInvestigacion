package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoLine;
import ec.edu.espe.GrupoInvestigacion.dto.DtoLineGetArea;
import ec.edu.espe.GrupoInvestigacion.service.IServiceLine;
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

import java.util.List;

@WebMvcTest(LineController.class)
public class LineControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/line";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceLine lineService;

    @Test
    void getAllLine_ShouldReturnOkAndList() throws Exception {
        DtoLine dtoLine = new DtoLine();
        dtoLine.setNombreLinea("Test Line");
        List<DtoLine> dtoList = List.of(dtoLine);

        Mockito.when(lineService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreLinea").value("Test Line"));
    }

    @Test
    void getLineById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoLine dtoLine = new DtoLine();
        dtoLine.setIdLinea(id);
        dtoLine.setNombreLinea("Test Line");

        Mockito.when(lineService.find(id)).thenReturn(dtoLine);

        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreLinea").value("Test Line"));
    }

    @Test
    void createLine_ShouldReturnCreatedAndId() throws Exception {
        DtoLine dtoLine = new DtoLine();
        dtoLine.setNombreLinea("New Line");

        Mockito.when(lineService.save(Mockito.any(DtoLine.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombreLinew\": \"New line\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    /*
    @Test
    void getAreaByLine_ShouldReturnDtoWhenExists() throws Exception {
       Long id = 1L;
       DtoLineGetArea dtoLineGetArea = new DtoLineGetArea();
       DtoLine dtoLine = new DtoLine();
       dtoLine.setIdArea(id);
       dtoLine.setNombreLinea("Test Line");

       Mockito.when(lineService.findAreaByLine(id)).thenReturn((DtoLineGetArea) List.of(dtoLineGetArea));
       mockMvc.perform(get(PATH+"/getLineByArea/{id}",id))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].nombreLinea").value("Test Line"));
    }*/

    @Test
    void updateLine_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoLine dtoLine = new DtoLine();
        dtoLine.setIdLinea(id);

        Mockito.when(lineService.find(id)).thenReturn(dtoLine);

        mockMvc.perform(put(PATH+"/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombreLinew\": \"Updated line\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(lineService, times(1)).update(Mockito.any(DtoLine.class));
    }

    /*
    @Test
    void getLineByArea_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoLine dtoLine = new DtoLine();
        dtoLine.setIdArea(id);

        Mockito.when(lineService.findLineByArea(id)).thenReturn(dtoLine);
    }*/

}
