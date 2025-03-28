package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DtoEvaluationsReport {
    private Long id;
    private Float cumplimientoEstrategico;
    private Float cumplimientoOperativo;
    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;
    private Long idGrupoInvestigacion;


   // private List<Long> idsEvidencias;
}
