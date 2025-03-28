package ec.edu.espe.GrupoInvestigacion.dto;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class DtoClosure {
    private Long id;
    private String objCierre;
    private String contexto;
    private String objInicial;
    private String actividades;
    private String impactoAcad;
    private String impactoFin;
    private String reubicacion;
    private String conclusion;
    private String recomendacion;
    private String proceso;
    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;
    private Long idGrupoInvestigacion;
}
