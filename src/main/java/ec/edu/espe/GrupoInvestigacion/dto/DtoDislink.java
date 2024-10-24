package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.Date;
@Data

public class DtoDislink {
    private Long idDesvinculacion ;
    private Long idGrupoInv;
    private String justificacion;
    private String observaciones;
    private String cedula;
    private Character estado;
    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;
}
