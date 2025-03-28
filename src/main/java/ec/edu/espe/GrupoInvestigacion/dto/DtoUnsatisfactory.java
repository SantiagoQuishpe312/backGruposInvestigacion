package ec.edu.espe.GrupoInvestigacion.dto;
import lombok.Data;
import java.util.Date;

@Data
public class DtoUnsatisfactory {
    private Long id;
    private Long idCierre; // Relación con el modelo 'ModelClosure'
    private String registro;
    private String criteriosNoSatisfactorio;
    private String accionesNoSatisfactorio;
    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;
}
