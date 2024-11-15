package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup_Line;
import ec.edu.espe.GrupoInvestigacion.service.IServiceInvGroup_Line;
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

@WebMvcTest(InvGroup_LineController.class)
public class InvGroup_LineControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/invGroup_lines";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceInvGroup_Line invGroupLineService;

    @Test
    void getAllInvGroupLines_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoInvGroup_Line dtoInvGroupLine = new DtoInvGroup_Line();
        dtoInvGroupLine.setIdLinea(id);
        List<DtoInvGroup_Line> dtoList = List.of(dtoInvGroupLine);

        Mockito.when(invGroupLineService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idLinea").value(id));
    }

    @Test
    void getInvGroupLineById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoInvGroup_Line dtoInvGroupLine = new DtoInvGroup_Line();
        dtoInvGroupLine.setIdGrupo(id);
        List<DtoInvGroup_Line> dtoList = List.of(dtoInvGroupLine);

        Mockito.when(invGroupLineService.find(id)).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idGrupo").value(id));
    }

    @Test
    void createInvGroupLine_ShouldReturnCreatedAndId() throws Exception {
        Long id = 1L;
        DtoInvGroup_Line dtoInvGroupLine = new DtoInvGroup_Line();
        dtoInvGroupLine.setIdLinea(id);

        Mockito.when(invGroupLineService.save(Mockito.any(DtoInvGroup_Line.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idLinea\": \"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }
}
