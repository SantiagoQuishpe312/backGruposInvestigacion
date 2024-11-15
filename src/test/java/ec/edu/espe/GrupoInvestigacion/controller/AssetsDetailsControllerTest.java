package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoAssets_Details;
import ec.edu.espe.GrupoInvestigacion.service.IServiceAssets_Details;
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


@WebMvcTest(AssetsDetailsController.class)
public class AssetsDetailsControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/assetsDetails/";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceAssets_Details assets_detailsService;

    @Test
    void getAllAssetsDetails_ShouldReturnOkAndList() throws Exception {
        DtoAssets_Details dtoAssetsDetails = new DtoAssets_Details();
        dtoAssetsDetails.setCodigo("Test Assets Details");
        List<DtoAssets_Details> dtoList = List.of(dtoAssetsDetails);

        Mockito.when(assets_detailsService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo").value("Test Assets Details"));
    }

    @Test
    void getAssetsDetailsById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoAssets_Details dtoAssetsDetails = new DtoAssets_Details();
        dtoAssetsDetails.setIdDetalles(id);
        dtoAssetsDetails.setCodigo("Test Assets Details");

        Mockito.when(assets_detailsService.find(id)).thenReturn(dtoAssetsDetails);

        mockMvc.perform(get(PATH+"/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("Test Assets Details"));
    }

    @Test
    void createAssetsDetails_ShouldReturnCreatedAndId() throws Exception {
        DtoAssets_Details dtoAssetsDetails = new DtoAssets_Details();
        dtoAssetsDetails.setCodigo("New Assets Details");

        Mockito.when(assets_detailsService.save(dtoAssetsDetails)).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"codigo\": \"New Assets Details\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateAssetsDetails_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoAssets_Details dtoAssetsDetails = new DtoAssets_Details();
        dtoAssetsDetails.setIdDetalles(id);

        Mockito.when(assets_detailsService.find(id)).thenReturn(dtoAssetsDetails);

        mockMvc.perform(put(PATH+"/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"codigo\": \"Updated Assets Details\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(assets_detailsService, times(1)).update(Mockito.any(DtoAssets_Details.class));
    }
}
