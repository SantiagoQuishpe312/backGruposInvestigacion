package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDevelopmentPlan;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDevelopmentPlanGetData;
import ec.edu.espe.GrupoInvestigacion.dto.DtoGroupRegForm;

import java.util.List;

public interface IServiceDevelopmentPlan {
    public List<DtoDevelopmentPlan> findAll();

    public DtoDevelopmentPlan find(Long id);
    public List<DtoDevelopmentPlan> findGroupC(Long id);
    public List<DtoDevelopmentPlan> findGroupType(Long id,Character tipo);
    public DtoDevelopmentPlanGetData findAllByGroupTypeState(Long id, Character tipo,Character estado);

    Long create(DtoDevelopmentPlan dtoDevelopmentPlan);
    void update(DtoDevelopmentPlan dtoDevelopmentPlan);
}
