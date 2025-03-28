package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.Date;
@Data

public class DtoDislinkMember {
    private Long idGrupoInv;
    private Long idUsuario;
    private String tipo;
    private Date fechaVinculacion;
    private String status;
    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;
}
