package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoInvMember;
import ec.edu.espe.GrupoInvestigacion.service.IServiceInvMember;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.PRIVATE_PATH;
import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.V1_API_VERSION;
import static java.nio.file.Files.delete;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(InvMemberController.class)
public class InvMemberControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/inv-members";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceInvMember invMemberService;

    @Test
    void getAllInvMembers_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoInvMember dtoInvMember = new DtoInvMember();
        dtoInvMember.setIdUsuario(id);
        List<DtoInvMember> dtoInvMembers = List.of(dtoInvMember);

        Mockito.when(invMemberService.findAll()).thenReturn(dtoInvMembers);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUsuario").value(id));
    }

    @Test
    void getInvMemberById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoInvMember dtoInvMember = new DtoInvMember();
        dtoInvMember.setIdUsuario(id);

        Mockito.when(invMemberService.find(id)).thenReturn(dtoInvMember);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(id));
    }

    @Test
    void getInvMemberGroup_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoInvMember dtoInvMember = new DtoInvMember();
        dtoInvMember.setIdGrupoInv(id);
        List<DtoInvMember> dtoList = List.of(dtoInvMember);

        Mockito.when(invMemberService.findGroup(id)).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/group/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idGrupoInv").value(id));
    }

    @Test
    void createInvMember_ShouldReturnCreatedAndId() throws Exception {
        Long id = 1L;
        DtoInvMember dtoInvMember = new DtoInvMember();
        dtoInvMember.setIdUsuario(id);

        Mockito.when(invMemberService.save(Mockito.any(DtoInvMember.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idUsuario\": \"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    /*
    @Test
    void deleteInvMember_ShouldReturnOkAndList() throws Exception {
        Long idUser = 1L;
        Long idGrupoInv = 2L;
        DtoInvMember dtoInvMember = new DtoInvMember();
        dtoInvMember.setIdUsuario(idUser);
        dtoInvMember.setIdGrupoInv(idGrupoInv);

        Mockito.when(invMemberService.deleteUser(idUser, idGrupoInv)).thenReturn(true);
        mockMvc.perform(delete(PATH + "/"))
    }*/
}
