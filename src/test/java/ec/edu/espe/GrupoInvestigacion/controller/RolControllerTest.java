package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoRol;
import ec.edu.espe.GrupoInvestigacion.service.IServiceRol;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@WebMvcTest(RolController.class)
public class RolControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/rol";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceRol rolService;

    @Test
    void getAllRol_ShouldReturnOkAndList() throws Exception {
        DtoRol dtoRol = new DtoRol();
        dtoRol.setNombreRol("Test Rol");
        List<DtoRol> dtoList = List.of(dtoRol);

        Mockito.when(rolService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreRol").value("Test Rol"));
    }

    @Test
    void getRolById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoRol dtoRol = new DtoRol();
        dtoRol.setIdRol(id);
        dtoRol.setNombreRol("Test Rol");

        Mockito.when(rolService.find(id)).thenReturn(dtoRol);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreRol").value("Test Rol"));
    }
}
