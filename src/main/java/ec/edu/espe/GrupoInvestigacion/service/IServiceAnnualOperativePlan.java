package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoAnnualOperativePlan;
import ec.edu.espe.GrupoInvestigacion.dto.DtoAnnualOpGetControl;
import ec.edu.espe.GrupoInvestigacion.dto.DtoAnnualOperativePlan;

import java.util.List;

public interface IServiceAnnualOperativePlan {
    public List<DtoAnnualOperativePlan> findAll();
    public DtoAnnualOperativePlan findById(Long id);
    public DtoAnnualOpGetControl findDoc(Long id);
    Long save(DtoAnnualOperativePlan dtoAnnualOperativePlan);
    void update(DtoAnnualOperativePlan dtoAnnualOperativePlan);
}
