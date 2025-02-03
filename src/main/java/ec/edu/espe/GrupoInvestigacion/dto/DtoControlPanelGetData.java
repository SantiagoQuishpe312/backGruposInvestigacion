package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

@Data
public class DtoControlPanelGetData {
    private DtoControlPanel PanelControl;
    private DtoSpecificObjectives ObjetivoEspecífico;
    private DtoUser Responsable;
}
