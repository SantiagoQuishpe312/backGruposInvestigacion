package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup_AcademicDomain;
import ec.edu.espe.GrupoInvestigacion.service.IServiceInvGroup_AcademicDomain;
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

@WebMvcTest(InvGroup_AcademicDomainController.class)
public class InvGroup_AcademicDomainControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/invGroup_academicDomain";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceInvGroup_AcademicDomain invGroupAcademicDomainService;

    @Test
    void getAllInvGroupAcademicDomain_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoInvGroup_AcademicDomain dtoInvGroupAcademicDomain = new DtoInvGroup_AcademicDomain();
        dtoInvGroupAcademicDomain.setIdDomAcad(id);
        List<DtoInvGroup_AcademicDomain> dtoList = List.of(dtoInvGroupAcademicDomain);

        Mockito.when(invGroupAcademicDomainService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idDomAcad").value(id));
    }

    @Test
    void getInvGroupAcademicDomainById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoInvGroup_AcademicDomain dto = new DtoInvGroup_AcademicDomain();
        dto.setIdGrupo(id);
        List<DtoInvGroup_AcademicDomain> dtoList = List.of(dto);

        Mockito.when(invGroupAcademicDomainService.find(id)).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idGrupo").value(id));
    }

    @Test
    void createInvGroupAcademicDomain_ShouldReturnCreatedAndId() throws Exception {
        Long id = 1L;
        DtoInvGroup_AcademicDomain dto = new DtoInvGroup_AcademicDomain();
        dto.setIdDomAcad(id);

        Mockito.when(invGroupAcademicDomainService.save(Mockito.any(DtoInvGroup_AcademicDomain.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idDomAcad\": \"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }


}
