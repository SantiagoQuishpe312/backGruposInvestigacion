package ec.edu.espe.GrupoInvestigacion.controller;
import ec.edu.espe.GrupoInvestigacion.dto.DtoBookChapter;
import ec.edu.espe.GrupoInvestigacion.service.IServiceBookChapter;
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

@WebMvcTest(BookChapterController.class)
public class BookChapterControllerTest {
    private static final String PATH = V1_API_VERSION + PRIVATE_PATH  +"/book-chapters";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IServiceBookChapter bookChapterService;

    @Test
    void getAllBookChapters_ShouldReturnOkAndList() throws Exception {
        DtoBookChapter dtoBookChapter = new DtoBookChapter();
        dtoBookChapter.setTitulo("Test Book Chapter");
        List<DtoBookChapter> dtoList = List.of(dtoBookChapter);

        Mockito.when(bookChapterService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(PATH+"/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Test Book Chapter"));
    }

    @Test
    void getBookChapterById_ShouldReturnDtoWhenExists() throws Exception {
        Long id = 1L;
        DtoBookChapter dtoBookChapter = new DtoBookChapter();
        dtoBookChapter.setIdLibroCapitulo(id);
        dtoBookChapter.setTitulo("Test Book Chapter");

        Mockito.when(bookChapterService.find(id)).thenReturn(dtoBookChapter);

        mockMvc.perform(get(PATH+"/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Test Book Chapter"));
    }

    @Test
    void createBookChapter_ShouldReturnCreatedAndId() throws Exception {
        DtoBookChapter dtoBookChapter = new DtoBookChapter();
        dtoBookChapter.setTitulo("New Book Chapter");

        Mockito.when(bookChapterService.save(Mockito.any(DtoBookChapter.class))).thenReturn(1L);

        mockMvc.perform(post(PATH+"/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"titulo\": \"New Book Chapter\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void updateBookChapter_ShouldReturnAccepted_WhenEntityExists() throws Exception {
        Long id = 1L;
        DtoBookChapter dtoBookChapter = new DtoBookChapter();
        dtoBookChapter.setIdLibroCapitulo(id);

        Mockito.when(bookChapterService.find(id)).thenReturn(dtoBookChapter);

        mockMvc.perform(put(PATH+"/update/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"titulo\": \"New Book Chapter\"}"))
                .andExpect(status().isAccepted());

        Mockito.verify(bookChapterService, times(1)).update(Mockito.any(DtoBookChapter.class));

    }

}
