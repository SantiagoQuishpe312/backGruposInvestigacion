package ec.edu.espe.GrupoInvestigacion.controller;
import ec.edu.espe.GrupoInvestigacion.dto.DtoObj_Strategies_ODS;
import ec.edu.espe.GrupoInvestigacion.service.IServiceObj_Strategies_ODS;
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

@WebMvcTest(Obj_Strategies_ODSController.class)
public class Obj_Strategies_ODSControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/obj_strategies_ods";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IServiceObj_Strategies_ODS obj_strategies_odsService;

    @Test
    void getAllObjStrategiesODSByIdObj_ShouldReturnOkAndList() throws Exception {
        Long id = 1L;
        DtoObj_Strategies_ODS dtoObjStrategiesOds = new DtoObj_Strategies_ODS();
        dtoObjStrategiesOds.setIdObjetivoEspecifico(id);
        List<DtoObj_Strategies_ODS> dtoList = List.of(dtoObjStrategiesOds);

        Mockito.when(obj_strategies_odsService.findByObj(id)).thenReturn(dtoList);
        mockMvc.perform(get(PATH + "/objective/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idObjetivoEspecifico").value(id));
    }


    @Test
    void createObjStrategiesODS_ShouldReturnCreatedAndId() throws Exception {
        Long id = 1L;
        DtoObj_Strategies_ODS dtoObjStrategiesOds = new DtoObj_Strategies_ODS();
        dtoObjStrategiesOds.setIdEstrategia(id);

        Mockito.when(obj_strategies_odsService.save(Mockito.any(DtoObj_Strategies_ODS.class))).thenReturn(1L);
        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idEstrategia\": \"New Obj\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }
}
