package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoAnnexes;
import ec.edu.espe.GrupoInvestigacion.service.IServiceAnnexes;
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

//AnnexesControllerTest.java
@WebMvcTest(AnnexesController.class)
public class AnnexesControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  + "/annexes";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceAnnexes annexesService;

    @Test
    void getAllAnnexes_ShouldReturnOkAndList() throws Exception {
        DtoAnnexes dtoAnnexes = new DtoAnnexes();
        dtoAnnexes.setNombreAnexo("Test Annexes");
        List<DtoAnnexes> dtoList = List.of(dtoAnnexes);

        Mockito.when(annexesService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreAnexo").value("Test Annexes"));
    }

    @Test
    void getAnnexesById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoAnnexes dtoAnnexes = new DtoAnnexes();
        dtoAnnexes.setIdAnexo(id);
        dtoAnnexes.setNombreAnexo("Test Annexes");

        Mockito.when(annexesService.find(id)).thenReturn(dtoAnnexes);

        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreAnexo").value("Test Annexes"));

    }

    @Test
    void createAnnexes_ShouldReturnCreatedAndId() throws Exception {
        DtoAnnexes dtoAnnexes = new DtoAnnexes();
        dtoAnnexes.setNombreAnexo("New Annexes");

        Mockito.when(annexesService.create(Mockito.any(DtoAnnexes.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombreAnexo\": \"New Annexes\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateAnnexes_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoAnnexes dtoAnnexes = new DtoAnnexes();
        dtoAnnexes.setIdAnexo(id);

        Mockito.when(annexesService.find(id)).thenReturn(dtoAnnexes);

        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombreAnexo\": \"Updated Annexes\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(annexesService,times(1)).update(Mockito.any(DtoAnnexes.class));
    }
}
