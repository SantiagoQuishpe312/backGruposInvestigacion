package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoResearchProject;
import ec.edu.espe.GrupoInvestigacion.service.IServiceResearchProjec;
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

@WebMvcTest(ResearchProjecController.class)
public class ResearchProjecControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/research-projects";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceResearchProjec researchProjecService;

    @Test
    void getAllResearchProjects_ShouldReturnOkAndList() throws Exception {
        DtoResearchProject dtoResearchProject = new DtoResearchProject();
        dtoResearchProject.setTitulo("Test Research Project");
        List<DtoResearchProject> dtoList = List.of(dtoResearchProject);

        Mockito.when(researchProjecService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Test Research Project"));
    }

    @Test
    void getResearchProjectById_ShouldReturnDtoWhenExistst() throws Exception {
        Long id = 1L;
        DtoResearchProject dtoResearchProject = new DtoResearchProject();
        dtoResearchProject.setIdProyecto(id);
        dtoResearchProject.setTitulo("Test Research Project");

        Mockito.when(researchProjecService.find(id)).thenReturn(dtoResearchProject);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Test Research Project"));
    }

    @Test
    void createResearchProject_ShouldReturnCreatedAndId() throws Exception {
        DtoResearchProject dtoResearchProject = new DtoResearchProject();
        dtoResearchProject.setTitulo("New Research Project");

        Mockito.when(researchProjecService.save(Mockito.any(DtoResearchProject.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"titulo\": \"New Research Project\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateResearchProject_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoResearchProject dtoResearchProject = new DtoResearchProject();
        dtoResearchProject.setIdProyecto(id);

        Mockito.when(researchProjecService.find(id)).thenReturn(dtoResearchProject);
        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"titulo\": \"Updated Research Project\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(researchProjecService,times(1)).update(Mockito.any(DtoResearchProject.class));
    }
}
