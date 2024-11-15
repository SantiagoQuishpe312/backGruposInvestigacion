package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup;
import ec.edu.espe.GrupoInvestigacion.service.IServiceInvGroup;
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

@WebMvcTest(InvGroupController.class)
public class InvGroupControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  + "/inv-group";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceInvGroup invGroupService;

    @Test
    void getAllInvGroups_ShouldReturnOkAndList() throws Exception {
        DtoInvGroup dtoInvGroup = new DtoInvGroup();
        dtoInvGroup.setNombreGrupoInv("Test InvGroup");
        List<DtoInvGroup> dtoList = List.of(dtoInvGroup);

        Mockito.when(invGroupService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreGrupoInv").value("Test InvGroup"));
    }

    @Test
    void getInvGroupById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoInvGroup dtoInvGroup = new DtoInvGroup();
        dtoInvGroup.setIdGrupoInv(id);
        dtoInvGroup.setNombreGrupoInv("Test InvGroup");

        Mockito.when(invGroupService.find(id)).thenReturn(dtoInvGroup);

        mockMvc.perform(get(PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreGrupoInv").value("Test InvGroup"));
    }

    @Test
    void createInvGroup_ShouldReturnCreatedAndId() throws Exception {
        DtoInvGroup dtoInvGroup = new DtoInvGroup();
        dtoInvGroup.setNombreGrupoInv("New InvGroup");

        Mockito.when(invGroupService.save(Mockito.any(DtoInvGroup.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombreGrupoInv\": \"New InvGroup\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateInvGroup_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoInvGroup dtoInvGroup = new DtoInvGroup();
        dtoInvGroup.setIdGrupoInv(id);

        Mockito.when(invGroupService.find(id)).thenReturn(dtoInvGroup);

        mockMvc.perform(put(PATH + "/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombreGrupoInv\": \"New InvGroup\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(invGroupService, times(1)).update(Mockito.any(DtoInvGroup.class));
    }

    @Test
    void getInvGroupByUser_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoInvGroup dtoInvGroup = new DtoInvGroup();
        dtoInvGroup.setIdCoordinador(id);
        dtoInvGroup.setNombreGrupoInv("Test InvGroup");

        Mockito.when(invGroupService.findByUser(id)).thenReturn(dtoInvGroup);

        mockMvc.perform(get(PATH + "/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreGrupoInv").value("Test InvGroup"));
    }
}
