package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class DtoControlPanel {
    private Long idPanelControl;
    private Long idPlanDesarrollo;
    private Long idObjetivoEspecifico;
    private Long idResponsable;
    private String actividad;
    private String indicadorNombre;
    private String indicadorTipo;
    private String indicadorForma;
    private String indicadorCondicional;
    private String indicadorAcumulativo;
    private Float meta1;
    private Float meta2;
    private Float meta3;
    private Float meta4;
    private Float financiamiento;
    private String observacion;
    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModificacion;
    private Date fechaModificacion;
}
