package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoUser;
import ec.edu.espe.GrupoInvestigacion.service.IServiceUser;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH + "/user";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceUser userService;

    @Test
    void getAllUsers_ShouldReturnOkAndList() throws Exception {
        DtoUser dtoUser = new DtoUser();
        dtoUser.setNombre("Test user");
        List<DtoUser> dtoUsers = List.of(dtoUser);

        Mockito.when(userService.findAll()).thenReturn(dtoUsers);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Test user"));
    }

    @Test
    void getUserById_ShouldReturnDtoWhenExisits() throws Exception {
        Long id = 1L;
        DtoUser dtoUser = new DtoUser();
        dtoUser.setId(id);
        dtoUser.setNombre("Test Nombre");

        Mockito.when(userService.find(id)).thenReturn(dtoUser);

        mockMvc.perform(get(PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test Nombre"));
    }

    @Test
    void createUser_ShouldReturnCreatedUserAndId() throws Exception {
        DtoUser dtoUser = new DtoUser();
        dtoUser.setNombre("New user");

        Mockito.when(userService.save(Mockito.any(DtoUser.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"nombre\":\"New user\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

}