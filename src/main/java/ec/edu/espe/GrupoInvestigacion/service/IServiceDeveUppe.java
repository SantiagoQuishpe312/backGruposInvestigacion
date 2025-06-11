package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDevGetLegalFramework;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDevGetUpperLevelPlan;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDeveUppe;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUpperLevelPlan;

import java.util.List;

public interface IServiceDeveUppe {
    public List<DtoDeveUppe> findAll();
    public List<DtoDeveUppe> find(Long id);
    Long save(DtoDeveUppe dtoDeveUppe);
    public List<DtoUpperLevelPlan> findByDev(Long id);
    void delete(Long developmentId, Long id);

}
