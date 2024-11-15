package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDeveUppe;
import ec.edu.espe.GrupoInvestigacion.service.IServiceDeveUppe;
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

@WebMvcTest(DeveUppeController.class)
public class DeveUppeControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/deve-uppes";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceDeveUppe deveUppeService;

    @Test
    void getAllDeveUppes_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoDeveUppe dtoDeveUppe = new DtoDeveUppe();
        dtoDeveUppe.setIdPlanNivelSuperior(id);
        List<DtoDeveUppe> dtoList = List.of(dtoDeveUppe);

        Mockito.when(deveUppeService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPlanNivelSuperior").value(id));
    }

    @Test
    void getDeveUppeById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoDeveUppe dtoDeveUppe = new DtoDeveUppe();
        dtoDeveUppe.setIdPlan(id);
        List<DtoDeveUppe> dtoList = List.of(dtoDeveUppe);

        Mockito.when(deveUppeService.find(id)).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPlan").value(id));
    }

    @Test
    void createDeveUppe_ShouldReturnCreatedAndId() throws Exception {
        Long id = 1L;
        DtoDeveUppe dtoDeveUppe = new DtoDeveUppe();
        dtoDeveUppe.setIdPlanNivelSuperior(id);

        Mockito.when(deveUppeService.save(Mockito.any(DtoDeveUppe.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idPlanNivelSuperior\": \"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }
}
