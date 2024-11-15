package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoLink;
import ec.edu.espe.GrupoInvestigacion.service.IServiceLink;
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

@WebMvcTest(LinkController.class)
public class LinkControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/links";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceLink linkService;

    @Test
    void getAllLinks_ShouldReturnOkAndList() throws Exception {
        DtoLink dtoLink = new DtoLink();
        dtoLink.setJustificacion("Test Link");
        List<DtoLink> dtoList = List.of(dtoLink);

        Mockito.when(linkService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].justificacion").value("Test Link"));
    }

    @Test
    void getLinkById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoLink dtoLink = new DtoLink();
        dtoLink.setIdVinculacion(id);
        dtoLink.setJustificacion("Test Link");

        Mockito.when(linkService.find(id)).thenReturn(dtoLink);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.justificacion").value("Test Link"));
    }

    @Test
    void getLinkByState_ShouldReturnDtoWhenExists() throws Exception {
        Character state = 'A';
        DtoLink dtoLink = new DtoLink();
        dtoLink.setEstado(state);
        dtoLink.setJustificacion("Test Link");
        List<DtoLink> dtoList = List.of(dtoLink);

        Mockito.when(linkService.findByState(state)).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/state/{estado}",state))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].justificacion").value("Test Link"));
    }

    @Test
    void createLink_ShouldReturnCreatedAndId() throws Exception {
        DtoLink dtoLink = new DtoLink();
        dtoLink.setJustificacion("New Link");

        Mockito.when(linkService.save(Mockito.any(DtoLink.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"justificacion\": \"New Link\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateLink_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoLink dtoLink = new DtoLink();
        dtoLink.setIdVinculacion(id);

        Mockito.when(linkService.find(id)).thenReturn(dtoLink);

        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"justificacion\": \"New Link\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(linkService, times(1)).update(Mockito.any(DtoLink.class));
    }
}
