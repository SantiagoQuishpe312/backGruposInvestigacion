package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup_Area;
import ec.edu.espe.GrupoInvestigacion.service.IServiceInvGroup_Area;
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

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(InvGroup_AreaController.class)
public class InvGroup_AreaControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/invGroup_area";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceInvGroup_Area invGroupAreaService;

    @Test
    void getAllInvGroupArea_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoInvGroup_Area dtoInvGroupArea = new DtoInvGroup_Area();
        dtoInvGroupArea.setIdGrupo(id);
        List<DtoInvGroup_Area> dtoList = List.of(dtoInvGroupArea);

        Mockito.when(invGroupAreaService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idGrupo").value(id));
    }

    @Test
    void getInvGroupAreaById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoInvGroup_Area dtoInvGroupArea = new DtoInvGroup_Area();
        dtoInvGroupArea.setIdArea(id);
        List<DtoInvGroup_Area> dtoList = List.of(dtoInvGroupArea);

        Mockito.when(invGroupAreaService.find(id)).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idArea").value(id));
    }

    @Test
    void createInvGroupArea_ShouldReturnCreatedAndId() throws Exception {
        Long id = 1L;
        DtoInvGroup_Area dtoInvGroupArea = new DtoInvGroup_Area();
        dtoInvGroupArea.setIdGrupo(id);

        Mockito.when(invGroupAreaService.save(Mockito.any(DtoInvGroup_Area.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idGrupo\": \"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

    }
}
