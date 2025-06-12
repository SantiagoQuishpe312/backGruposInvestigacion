package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

@Data
public class DtoControlPanelGetData {
    private DtoControlPanel PanelControl;
    private DtoSpecificObjectives ObjetivoEspecifico;
    private DtoUser Responsable;
}
