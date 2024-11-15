package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoBudgetExecute;
import ec.edu.espe.GrupoInvestigacion.service.IServiceBudgetExecute;
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

@WebMvcTest(BudgetExecuteController.class)
public class BudgetExecuteControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  + "/budget-executes";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceBudgetExecute budgetExecuteService;

    @Test
    void getAllBudgetExecutes_ShouldReturnOkAndList() throws Exception {
        DtoBudgetExecute dtoBudgetExecute = new DtoBudgetExecute();
        dtoBudgetExecute.setActividad("Test Budget Execute");
        List<DtoBudgetExecute> dtoList = List.of(dtoBudgetExecute);

        Mockito.when(budgetExecuteService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].actividad").value("Test Budget Execute"));
    }

    @Test
    void getBudgetExecuteById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoBudgetExecute dtoBudgetExecute = new DtoBudgetExecute();
        dtoBudgetExecute.setIdPresupuesto(id);
        dtoBudgetExecute.setActividad("Test Budget Execute");

        Mockito.when(budgetExecuteService.find(id)).thenReturn(dtoBudgetExecute);

        mockMvc.perform(get(PATH+"/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actividad").value("Test Budget Execute"));
    }

    @Test
    void createBudgetExecute_ShouldReturnCreatedAndId() throws Exception {
        DtoBudgetExecute dtoBudgetExecute = new DtoBudgetExecute();
        dtoBudgetExecute.setActividad("Test Budget Execute");

        Mockito.when(budgetExecuteService.save(Mockito.any(DtoBudgetExecute.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"actividad\": \"New Budget Execute\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateBudgetExecute_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoBudgetExecute dtoBudgetExecute = new DtoBudgetExecute();
        dtoBudgetExecute.setIdPresupuesto(id);

        Mockito.when(budgetExecuteService.find(id)).thenReturn(dtoBudgetExecute);

        mockMvc.perform(put(PATH+"/update/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"actividad\": \"New Budget Execute\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(budgetExecuteService, times(1)).update(Mockito.any(DtoBudgetExecute.class));
    }
}
