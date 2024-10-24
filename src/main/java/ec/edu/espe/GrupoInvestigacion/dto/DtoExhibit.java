package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.Date;
@Data
public class DtoExhibit {
    private Long idExhibit;
    private Long idSolicitudCreacion;
    private String nombreExhibit;
    private String rutaExhibit;
    private String usuarioCreacionExhibit;
    private Date fechaCreacionExhibit;
    private String usuarioModificacionExhibit;
    private Date fechaModificacionExhibit;
}
