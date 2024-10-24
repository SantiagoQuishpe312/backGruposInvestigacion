package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DtoStrategies {
    private Long idEstrategia;
    private Long idObjetivo;
    private String estrategia;
    private String ods;
    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;
}
