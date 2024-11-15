package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoLegalFramework;
import ec.edu.espe.GrupoInvestigacion.service.IServiceLegalFramework;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.V1_API_VERSION;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.List;
import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.PRIVATE_PATH;

@WebMvcTest(LegalFrameworkController.class)
public class LegalFrameworkControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/legal-frameworks";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceLegalFramework legalFrameworkService;

    @Test
    void getAllLegalFramework_ShouldReturnOkAndList() throws Exception {
        DtoLegalFramework dtoLegalFramework = new DtoLegalFramework();
        dtoLegalFramework.setNombre("Test Legal Framework");
        List<DtoLegalFramework> dtoList = List.of(dtoLegalFramework);

        Mockito.when(legalFrameworkService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Test Legal Framework"));
    }

    @Test
    void getLegalFrameworkById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoLegalFramework dtoLegalFramework = new DtoLegalFramework();
        dtoLegalFramework.setIdMarcoLegal(id);
        dtoLegalFramework.setNombre("Test Legal Framework");

        Mockito.when(legalFrameworkService.find(id)).thenReturn(dtoLegalFramework);

        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test Legal Framework"));
    }

    @Test
    void createLegalFramework_ShouldReturnCreatedAndId() throws Exception {
        DtoLegalFramework dtoLegalFramework = new DtoLegalFramework();
        dtoLegalFramework.setNombre("New Legal Framework");

        Mockito.when(legalFrameworkService.save(Mockito.any(DtoLegalFramework.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombre\": \"New Legal Framework\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateLegalFramework_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoLegalFramework dtoLegalFramework = new DtoLegalFramework();
        dtoLegalFramework.setIdMarcoLegal(id);

        Mockito.when(legalFrameworkService.find(id)).thenReturn(dtoLegalFramework);

        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombre\": \"Updated Legal Framework\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(legalFrameworkService, times(1)).update(Mockito.any(DtoLegalFramework.class));
    }
}
