package ec.edu.espe.GrupoInvestigacion.dto;
import lombok.Data;
import java.util.Date;

@Data
public class DtoClosureRequest {
    private Long id;
    private String motivacion;
    private String justificacion;
    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;
    private Long idCierre; // Relación con el modelo 'ModelClosure'
}
