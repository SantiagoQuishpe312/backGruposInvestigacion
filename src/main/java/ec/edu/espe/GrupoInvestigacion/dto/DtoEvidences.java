package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;
import java.util.Date;

@Data
public class DtoEvidences {
    private Long id;
    private String numero;
    private Date fecha;
    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;
    private Long idAnexo;
    private Long idInformeEvaluacion;
}