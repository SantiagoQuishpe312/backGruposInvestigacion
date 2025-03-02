package ec.edu.espe.GrupoInvestigacion.dto;
import lombok.Data;

import java.util.Date;


@Data
public class DtoInvMember {
    private Long idGrupoInv;
    private Long idUsuario;
    private Boolean estado;
    private String tipo;
    private Date fechaVinculacion;
    private Date fechaDesvinculacion;
    private String status;
    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;

}
