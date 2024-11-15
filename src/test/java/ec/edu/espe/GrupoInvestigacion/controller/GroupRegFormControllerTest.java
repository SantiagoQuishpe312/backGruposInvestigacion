package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoGroupRegForm;
import ec.edu.espe.GrupoInvestigacion.service.IServiceGroupRegForm;
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

@WebMvcTest(GroupRegFormController.class)
public class GroupRegFormControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/group-reg-form";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceGroupRegForm groupRegFormService;

    @Test
    void getAllGroupRegForm_ShouldReturnOkAndList() throws Exception {
        DtoGroupRegForm dtoGroupRegForm = new DtoGroupRegForm();
        dtoGroupRegForm.setEstado('T');
        List<DtoGroupRegForm> dtoGroupRegFormList = List.of(dtoGroupRegForm);

        Mockito.when(groupRegFormService.findAll()).thenReturn(dtoGroupRegFormList);
        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("T"));
    }

    @Test
    void getGroupRegFormById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoGroupRegForm dtoGroupRegForm = new DtoGroupRegForm();
        dtoGroupRegForm.setIdFormularioRegistroGrupo(id);
        dtoGroupRegForm.setEstado('T');

        Mockito.when(groupRegFormService.find(id)).thenReturn(dtoGroupRegForm);
        mockMvc.perform(get(PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("T"));
    }

    @Test
    void createGroupRegForm_ShouldReturnCreatedAndId() throws Exception {
        DtoGroupRegForm dtoGroupRegForm = new DtoGroupRegForm();
        dtoGroupRegForm.setEstado('T');

        Mockito.when(groupRegFormService.create(Mockito.any(DtoGroupRegForm.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"estado\": \"T\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateGroupRegForm_ShouldReturnAccepted_WhenEntityExistsd() throws Exception {
        Long id = 1L;
        DtoGroupRegForm dtoGroupRegForm = new DtoGroupRegForm();
        dtoGroupRegForm.setIdFormularioRegistroGrupo(id);

        Mockito.when(groupRegFormService.find(id)).thenReturn(dtoGroupRegForm);
        mockMvc.perform(put(PATH + "/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"estado\": \"T\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(groupRegFormService, times(1)).update(Mockito.any(DtoGroupRegForm.class));
    }
}
