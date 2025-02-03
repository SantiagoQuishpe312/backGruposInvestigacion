package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.List;
@Data
public class DtoDevelopmentPlanGetData {
    private DtoDevelopmentPlan PlanDesarrollo;
    private List<DtoUpperLevelPlan> PlanSuperior;
    private List<DtoLegalFramework> MarcoLegal;
    private List<DtoNationalPlan> PlanNacional;
    private DtoInstStrategicObj ObjEstrategicoInst;
    private List<DtoObjGetStrategies> ObjEstrategiasOds;
    private List<DtoControlPanelGetData> PanelControl;
}
