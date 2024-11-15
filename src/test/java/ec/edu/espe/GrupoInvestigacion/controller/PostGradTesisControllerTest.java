package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoPostGradTesis;
import ec.edu.espe.GrupoInvestigacion.service.IServicePostGradTesis;
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

@WebMvcTest(PostGradTesisController.class)
public class PostGradTesisControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/post-grad-tesis";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServicePostGradTesis postGradTesisService;

    @Test
    void getAllPostGradTesis_ShouldReturnOkAndList() throws Exception {
        DtoPostGradTesis dtoPostGradTesis = new DtoPostGradTesis();
        dtoPostGradTesis.setNombre("Test PostGradTesis");
        List<DtoPostGradTesis> dtoList = List.of(dtoPostGradTesis);

        Mockito.when(postGradTesisService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Test PostGradTesis"));
    }

    @Test
    void getPostGradTesisById_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoPostGradTesis dtoPostGradTesis = new DtoPostGradTesis();
        dtoPostGradTesis.setIdTesis(id);
        dtoPostGradTesis.setNombre("Test PostGradTesis");

        Mockito.when(postGradTesisService.find(id)).thenReturn(dtoPostGradTesis);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test PostGradTesis"));
    }

    @Test
    void createPostGradTesis_ShouldReturnCreatedAndId() throws Exception {
        DtoPostGradTesis dtoPostGradTesis = new DtoPostGradTesis();
        dtoPostGradTesis.setNombre("New PostGradTesis");

        Mockito.when(postGradTesisService.save(Mockito.any(DtoPostGradTesis.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombre\": \"New PostGradTesis\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updatePostGradTesis_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoPostGradTesis dtoPostGradTesis = new DtoPostGradTesis();
        dtoPostGradTesis.setIdTesis(id);

        Mockito.when(postGradTesisService.find(id)).thenReturn(dtoPostGradTesis);
        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombre\": \"New PostGradTesis\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(postGradTesisService, times(1)).update(Mockito.any(DtoPostGradTesis.class));
    }
}
