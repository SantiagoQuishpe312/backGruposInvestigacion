package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.Date;
@Data
public class DtoControlPanel {
    private Long idPanelControl;
    private Long idPlanDesarrollo;
    private String objetivoEspecifico;
    private String actividad;
    private String responsable;
    private String indicador;
    private String meta1;
    private String meta2;
    private String meta3;
    private String meta4;
    private Float financiamiento;
    private String observacion;
    private String usuarioCreacionPanelControl;
    private Date fechaCreacionPanelControl;
    private String usuarioModificacionPanelControl;
    private Date fechaModificacionPanelControl;
}
