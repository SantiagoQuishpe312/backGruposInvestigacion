package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoAcademicDomain;
import ec.edu.espe.GrupoInvestigacion.service.IServiceAcademicDomain;
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

// AcademicDomainControllerTest.java
@WebMvcTest(AcademicDomainController.class)
class AcademicDomainControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/academic-domains";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceAcademicDomain academicDomainService;

    @Test
    void getAllAcademicDomains_ShouldReturnOkAndList() throws Exception {
        DtoAcademicDomain dtoAcademicDomain = new DtoAcademicDomain();
        dtoAcademicDomain.setNombreDominioAcademico("Test Domain");
        List<DtoAcademicDomain> dtoList = List.of(dtoAcademicDomain);

        Mockito.when(academicDomainService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreDominioAcademico").value("Test Domain"));
    }

    @Test
    void getAcademicDomainById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoAcademicDomain dtoAcademicDomain = new DtoAcademicDomain();
        dtoAcademicDomain.setIdDomimioAcademico(id);
        dtoAcademicDomain.setNombreDominioAcademico("Test Domain");

        Mockito.when(academicDomainService.find(id)).thenReturn(dtoAcademicDomain);

        mockMvc.perform(get(PATH+"/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreDominioAcademico").value("Test Domain"));
    }

    @Test
    void createAcademicDomain_ShouldReturnCreatedAndId() throws Exception {
        DtoAcademicDomain dtoAcademicDomain = new DtoAcademicDomain();
        dtoAcademicDomain.setNombreDominioAcademico("New Domain");

        Mockito.when(academicDomainService.save(Mockito.any(DtoAcademicDomain.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreDominioAcademico\": \"New Domain\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateAcademicDomain_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoAcademicDomain dtoAcademicDomain = new DtoAcademicDomain();
        dtoAcademicDomain.setIdDomimioAcademico(id);

        Mockito.when(academicDomainService.find(id)).thenReturn(dtoAcademicDomain);

        mockMvc.perform(put(PATH+"/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreDominioAcademico\": \"Updated Domain\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(academicDomainService, times(1)).update(Mockito.any(DtoAcademicDomain.class));
    }
}
