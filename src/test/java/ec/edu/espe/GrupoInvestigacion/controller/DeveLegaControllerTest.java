package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDeveLega;
import ec.edu.espe.GrupoInvestigacion.service.IServiceDeveLega;
import org.h2.bnf.context.DbTableOrView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
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

@WebMvcTest(DeveLegaController.class)
public class DeveLegaControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/deve-legas";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceDeveLega deveLegaService;

    @Test
    void getAllDeveLegas_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoDeveLega dtoDeveLega = new DtoDeveLega();
        dtoDeveLega.setIdMarco(id);
        List<DtoDeveLega> dtoDeveLegas = List.of(dtoDeveLega);

        Mockito.when(deveLegaService.findAll()).thenReturn(dtoDeveLegas);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idMarco").value(id));
    }

    @Test
    void getDeveLegaById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoDeveLega dtoDeveLega = new DtoDeveLega();
        dtoDeveLega.setIdPlan(id);
        List<DtoDeveLega> dtoList = List.of(dtoDeveLega);

        Mockito.when(deveLegaService.find(id)).thenReturn(dtoList);
        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPlan").value(id));
    }

    @Test
    void createDeveLega_ShouldReturnCreatedAndId() throws Exception {
        Long id = 1L;
        DtoDeveLega dtoDeveLega = new DtoDeveLega();
        dtoDeveLega.setIdMarco(id);

        Mockito.when(deveLegaService.save(Mockito.any(DtoDeveLega.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idMarco\": \"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }



}
