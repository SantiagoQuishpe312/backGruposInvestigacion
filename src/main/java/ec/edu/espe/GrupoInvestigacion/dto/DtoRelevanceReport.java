package ec.edu.espe.GrupoInvestigacion.dto;
import lombok.Data;

import java.util.Date;

@Data
public class DtoRelevanceReport {
    private Long idInformePertinencia;
    private Long idGrupo;
    private String numeroMemo;
    private Boolean formularioCreacion;
    private Boolean planDesarrollo;
    private Boolean documentosAdicionales;
    private String objetivos;
    private Boolean planEstrategico;
    private Boolean pertinenciaAcademicaAporte;
    private Boolean coordinador;
    private Boolean miembros;
    private Boolean objetivosPlanDesarrollo;
    private String conclusiones;
    private String recomendaciones;
    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;
}
