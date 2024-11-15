package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoOds;
import ec.edu.espe.GrupoInvestigacion.service.IServiceOds;
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

@WebMvcTest(OdsController.class)
public class OdsControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/ods";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceOds odsService;

    @Test
    void getAllOds_ShouldReturnOkAndList() throws Exception {
        DtoOds dtoOds = new DtoOds();
        dtoOds.setDescripcion("Test Ods");
        List<DtoOds> dtoOdsList = List.of(dtoOds);

        Mockito.when(odsService.findAll()).thenReturn(dtoOdsList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Test Ods"));
    }

    @Test
    void getOdsById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoOds dtoOds = new DtoOds();
        dtoOds.setId(id);
        dtoOds.setDescripcion("Test Ods");

        Mockito.when(odsService.find(id)).thenReturn(dtoOds);
        mockMvc.perform(get(PATH+"/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Test Ods"));
    }

    @Test
    void createOds_ShouldReturnCreatedAndId() throws Exception {
        DtoOds dtoOds = new DtoOds();
        dtoOds.setDescripcion("New Ods");

        Mockito.when(odsService.save(Mockito.any(DtoOds.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"descripcion\": \"New Ods\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateOds_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoOds dtoOds = new DtoOds();
        dtoOds.setId(id);

        Mockito.when(odsService.find(id)).thenReturn(dtoOds);

        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"descripcion\": \"New Ods\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(odsService, times(1)).update(Mockito.any(DtoOds.class));
    }
}
