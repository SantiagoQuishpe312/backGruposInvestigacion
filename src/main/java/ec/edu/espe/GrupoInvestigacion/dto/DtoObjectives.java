package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.Date;
@Data
public class DtoObjectives {
    private Long idObjetivo;
    private Long idPlanDesarrollo;
    private String objetivo;

    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;
}
