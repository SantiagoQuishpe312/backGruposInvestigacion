package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoObjGetStrategies;
import ec.edu.espe.GrupoInvestigacion.dto.DtoObjGetStrategiesRelation;
import ec.edu.espe.GrupoInvestigacion.dto.DtoObj_Strategies_ODS;

import java.util.List;

public interface IServiceObj_Strategies_ODS {
    public List<DtoObj_Strategies_ODS> findByObj(Long id);
    public List<DtoObj_Strategies_ODS> findByPlanRelations(Long id);
    Long save(DtoObj_Strategies_ODS dtoObjStrategiesOds);

    public List<DtoObjGetStrategies> findCompleteByPlan(Long id);
    public List<DtoObjGetStrategiesRelation> findCompleteByPlanRelations(Long id);

    public DtoObjGetStrategies findCompleteByObj(Long id);
 public void delete (Long idObj, Long idStrategy, Long idOds);

}
