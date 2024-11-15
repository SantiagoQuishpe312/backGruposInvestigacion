package ec.edu.espe.GrupoInvestigacion.controller;


import ec.edu.espe.GrupoInvestigacion.dto.DtoArea;
import ec.edu.espe.GrupoInvestigacion.service.IServiceArea;
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

@WebMvcTest(AreaController.class)
public class AreaControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  + "/areas";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceArea areaService;

    @Test
    void getAllArea_ShouldReturnOkAndList() throws Exception {
        DtoArea dtoArea = new DtoArea();
        dtoArea.setNombreArea("Test Area");
        List<DtoArea> dtoAreas = List.of(dtoArea);

        Mockito.when(areaService.findAll()).thenReturn(dtoAreas);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreArea").value("Test Area"));
    }

    @Test
    void getAreaById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoArea dtoArea = new DtoArea();
        dtoArea.setIdArea(id);
        dtoArea.setNombreArea("Test Area");

        Mockito.when(areaService.find(id)).thenReturn(dtoArea);

        mockMvc.perform(get(PATH+"/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreArea").value("Test Area"));

    }

    @Test
    void createArea_ShouldReturnCreatedAndId() throws Exception {
        DtoArea dtoArea = new DtoArea();
        dtoArea.setNombreArea("New Area");

        Mockito.when(areaService.save(Mockito.any(DtoArea.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombreArea\": \"New Area\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateArea_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoArea dtoArea = new DtoArea();
        dtoArea.setIdArea(id);

        Mockito.when(areaService.find(id)).thenReturn(dtoArea);

        mockMvc.perform(put(PATH+"/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombreArea\": \"Update Area\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(areaService, times(1)).update(Mockito.any(DtoArea.class));
    }

}
