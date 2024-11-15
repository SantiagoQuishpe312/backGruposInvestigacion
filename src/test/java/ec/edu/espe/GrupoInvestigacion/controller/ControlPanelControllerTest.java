package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoControlPanel;
import ec.edu.espe.GrupoInvestigacion.service.IServiceControlPanel;
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

@WebMvcTest(ControlPanelController.class)
public class ControlPanelControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/control-panel";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceControlPanel controlPanelService;

    @Test
    void getAllControlPanel_ShouldReturnOkAndList() throws Exception {
        DtoControlPanel dtoControlPanel = new DtoControlPanel();
        dtoControlPanel.setActividad("Test Control Panel");
        List<DtoControlPanel> dtoList = List.of(dtoControlPanel);

        Mockito.when(controlPanelService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].actividad").value("Test Control Panel"));
    }

    @Test
    void getControlPanelById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoControlPanel dtoControlPanel = new DtoControlPanel();
        dtoControlPanel.setIdPanelControl(id);
        dtoControlPanel.setActividad("Test Control Panel");

        Mockito.when(controlPanelService.find(id)).thenReturn(dtoControlPanel);

        mockMvc.perform(get(PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actividad").value("Test Control Panel"));
    }

    @Test
    void getControlPanelByDev_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoControlPanel dtoControlPanel = new DtoControlPanel();
        dtoControlPanel.setIdPlanDesarrollo(id);
        dtoControlPanel.setActividad("Test Control Panel");
        List<DtoControlPanel> dtoList = List.of(dtoControlPanel);

        Mockito.when(controlPanelService.findByDev(id)).thenReturn(dtoList);
        mockMvc.perform(get(PATH + "/bydev/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].actividad").value("Test Control Panel"));
    }

    @Test
    void createControlPanel_ShouldReturnCreatedAndId() throws Exception {
        DtoControlPanel dtoControlPanel = new DtoControlPanel();
        dtoControlPanel.setActividad("Test Control Panel");

        Mockito.when(controlPanelService.save(Mockito.any(DtoControlPanel.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"actividad\": \"New Control Panel\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateControlPanel_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoControlPanel dtoControlPanel = new DtoControlPanel();
        dtoControlPanel.setIdPlanDesarrollo(id);

        Mockito.when(controlPanelService.find(id)).thenReturn(dtoControlPanel);

        mockMvc.perform(put(PATH+"/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"actividad\": \"New Control Panel\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(controlPanelService, times(1)).update(Mockito.any(DtoControlPanel.class));
    }
}
