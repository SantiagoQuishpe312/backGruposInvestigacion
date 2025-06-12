package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.List;
@Data
public class DtoObjGetStrategiesRelation {
    private DtoSpecificObjectives obj;
    private List<DtoEstrategiaOds> estrategiasOds;
}
