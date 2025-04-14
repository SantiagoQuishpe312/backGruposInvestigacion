package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

@Data
public class DtoAnnualControlGetDetails {
    private DtoAnnualControl controlAnual;
    private DtoOds ods;
    private DtoStrategies estrategias;
    private DtoControlPanelGetData controlPanel;

}
