package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoCongress;
import ec.edu.espe.GrupoInvestigacion.service.IServiceCongress;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(CongressController.class)
public class CongressControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/congresses";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceCongress congressService;

    @Test
    void getAllCongresses_ShouldReturnOkAndList() throws Exception {
        DtoCongress dtoCongress = new DtoCongress();
        dtoCongress.setTitulo("Test Congress");
        List<DtoCongress> dtoList = List.of(dtoCongress);

        Mockito.when(congressService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Test Congress"));
    }

    @Test
    void getCongressById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoCongress dtoCongress = new DtoCongress();
        dtoCongress.setIdCongreso(id);
        dtoCongress.setTitulo("Test Congress");

        Mockito.when(congressService.find(id)).thenReturn(dtoCongress);

        mockMvc.perform(get(PATH+"/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Test Congress"));
    }

    @Test
    void createCongress_ShouldReturnCreatedAndId() throws Exception {
        DtoCongress dtoCongress = new DtoCongress();
        dtoCongress.setTitulo("New Congress");

        Mockito.when(congressService.save(Mockito.any(DtoCongress.class))).thenReturn(1l);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"titulo\": \"New Domain\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateCongress__ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoCongress dtoCongress = new DtoCongress();
        dtoCongress.setIdCongreso(id);

        Mockito.when(congressService.find(id)).thenReturn(dtoCongress);

        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"titulo\": \"New Domain\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(congressService, times(1)).update(Mockito.any(DtoCongress.class));
    }
}
