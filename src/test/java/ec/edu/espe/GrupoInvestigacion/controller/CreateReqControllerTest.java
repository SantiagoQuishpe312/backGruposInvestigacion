package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoCreationReq;
import ec.edu.espe.GrupoInvestigacion.service.IServiceCreationReq;
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

@WebMvcTest(CreationReqController.class)
public class CreateReqControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/creation-req";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceCreationReq creationReqService;

    @Test
    void getAllCreationReq_ShouldReturnOkAndList() throws Exception {
        DtoCreationReq dtoCreationReq = new DtoCreationReq();
        dtoCreationReq.setEstado('1');
        List<DtoCreationReq> dtoList = List.of(dtoCreationReq);

        Mockito.when(creationReqService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("1"));
    }

    @Test
    void getCreationReqById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoCreationReq dtoCreationReq = new DtoCreationReq();
        dtoCreationReq.setIdPeticionCreacion(id);
        dtoCreationReq.setEstado('1');

        Mockito.when(creationReqService.find(id)).thenReturn(dtoCreationReq);

        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("1"));
    }

    @Test
    void getCreationReqByGroup_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoCreationReq dtoCreationReq = new DtoCreationReq();
        dtoCreationReq.setIdGrupoInv(id);
        dtoCreationReq.setEstado('1');

        Mockito.when(creationReqService.findByGroup(id)).thenReturn(dtoCreationReq);

        mockMvc.perform(get(PATH+"/byGroup/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("1"));
    }

    @Test
    void createCreationReq_ShouldReturnCreatedAndId() throws Exception {
        DtoCreationReq dtoCreationReq = new DtoCreationReq();
        dtoCreationReq.setEstado('1');

        Mockito.when(creationReqService.save(Mockito.any(DtoCreationReq.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"estado\": \"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateCreationReq_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoCreationReq dtoCreationReq = new DtoCreationReq();
        dtoCreationReq.setIdGrupoInv(id);

        Mockito.when(creationReqService.find(id)).thenReturn(dtoCreationReq);

        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"estado\": \"1\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(creationReqService, times(1)).update(Mockito.any(DtoCreationReq.class));
    }
}
