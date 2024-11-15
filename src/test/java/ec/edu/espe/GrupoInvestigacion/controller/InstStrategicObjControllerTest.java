package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoInstStrategicObj;
import ec.edu.espe.GrupoInvestigacion.service.IServiceInstStrategicObj;
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

@WebMvcTest(InstStrategicObjController.class)
public class InstStrategicObjControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH + "/instStrategicObj/";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceInstStrategicObj instStrategicObjService;

    @Test
    void getAllInstStrategicOb_ShouldReturnOkAndList() throws Exception {
        DtoInstStrategicObj dtoInstStrategicObj = new DtoInstStrategicObj();
        dtoInstStrategicObj.setObjetivo("Test Objetivo");
        List<DtoInstStrategicObj> dtoList = List.of(dtoInstStrategicObj);

        Mockito.when(instStrategicObjService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].objetivo").value("Test Objetivo"));
    }

    @Test
    void getInstStrategicObjById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoInstStrategicObj dtoInstStrategicObj = new DtoInstStrategicObj();
        dtoInstStrategicObj.setIdObjetivoEstrategico(id);
        dtoInstStrategicObj.setObjetivo("Test Objetive");

        Mockito.when(instStrategicObjService.find(id)).thenReturn(dtoInstStrategicObj);

        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objetivo").value("Test Objetive"));
    }

    @Test
    void createInstStrategicObj_ShouldReturnCreatedAndId() throws Exception {
        DtoInstStrategicObj dtoInstStrategicObj = new DtoInstStrategicObj();
        dtoInstStrategicObj.setObjetivo("Test Objetive");

        Mockito.when(instStrategicObjService.save(Mockito.any(DtoInstStrategicObj.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"objetivo\": \"New Objetive\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateInstStrategicObj_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoInstStrategicObj dtoInstStrategicObj = new DtoInstStrategicObj();
        dtoInstStrategicObj.setIdObjetivoEstrategico(id);

        Mockito.when(instStrategicObjService.find(id)).thenReturn(dtoInstStrategicObj);

        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"objetivo\": \"Update Objetive\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(instStrategicObjService, times(1)).update(Mockito.any(DtoInstStrategicObj.class));
    }
}