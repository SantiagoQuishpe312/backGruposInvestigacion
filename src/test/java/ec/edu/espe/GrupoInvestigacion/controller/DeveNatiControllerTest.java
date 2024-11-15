package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDeveNati;
import ec.edu.espe.GrupoInvestigacion.service.IServiceDeveNati;
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

@WebMvcTest(DeveNatiController.class)
public class DeveNatiControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/deve-natis";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceDeveNati deveNatiService;

    @Test
    void getAllDeveNati_ShouldReturnOkAndList() throws Exception {
        Long id =1L;
        DtoDeveNati dtoDeveNati = new DtoDeveNati();
        dtoDeveNati.setIdPlanNacional(id);
        List<DtoDeveNati> dtoList = List.of(dtoDeveNati);

        Mockito.when(deveNatiService.findAll()).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPlanNacional").value(id));
    }

    @Test
    void getDeveNatiById_ShouldReturnDtoWhenExists() throws Exception {
        Long id =1L;
        DtoDeveNati dtoDeveNati = new DtoDeveNati();
        dtoDeveNati.setIdPlan(id);
        List<DtoDeveNati> dtoList = List.of(dtoDeveNati);

        Mockito.when(deveNatiService.find(id)).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPlan").value(id));
    }

    @Test
    void createDeveNati_ShouldReturnCreatedAndId() throws Exception {
        Long id =1L;
        DtoDeveNati dtoDeveNati = new DtoDeveNati();
        dtoDeveNati.setIdPlanNacional(id);

        Mockito.when(deveNatiService.save(Mockito.any(DtoDeveNati.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idPlanNacional\": \"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }
}
