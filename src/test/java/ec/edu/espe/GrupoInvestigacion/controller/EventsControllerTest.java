package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoEvents;
import ec.edu.espe.GrupoInvestigacion.service.IServiceEvents;
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

import java.util.List;

@WebMvcTest(EventsController.class)
public class EventsControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  + "/events";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceEvents eventsService;

    @Test
    void getAllEvents_ShouldReturnOkAndList() throws Exception {
        DtoEvents dtoEvents = new DtoEvents();
        dtoEvents.setNombre("Test Events");
        List<DtoEvents> dtList = List.of(dtoEvents);

        Mockito.when(eventsService.findAll()).thenReturn(dtList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Test Events"));
    }

    @Test
    void getEventById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoEvents dtoEvents = new DtoEvents();
        dtoEvents.setIdEvento(id);
        dtoEvents.setNombre("Test Events");

        Mockito.when(eventsService.find(id)).thenReturn(dtoEvents);

        mockMvc.perform(get(PATH+"/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test Events"));
    }

    @Test
    void createEvent_ShouldReturnCreatedAndId() throws Exception {
        DtoEvents dtoEvents = new DtoEvents();
        dtoEvents.setNombre("New Events");

        Mockito.when(eventsService.save(Mockito.any(DtoEvents.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombre\": \"New Events\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateEvent_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoEvents dtoEvents = new DtoEvents();
        dtoEvents.setIdEvento(id);

        Mockito.when(eventsService.find(id)).thenReturn(dtoEvents);

        mockMvc.perform(put(PATH+"/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombre\": \"Updated Events\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(eventsService, times(1)).update(Mockito.any(DtoEvents.class));
    }
}
