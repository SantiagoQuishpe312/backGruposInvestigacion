package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDevGetLegalFramework;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDevGetNationalPlan;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDeveNati;
import ec.edu.espe.GrupoInvestigacion.dto.DtoNationalPlan;

import java.util.List;

public interface IServiceDeveNati {
    public List<DtoDeveNati> findAll();
    public List<DtoDeveNati> find(Long id);
    Long save(DtoDeveNati dtoDeveNati);
    public List<DtoNationalPlan> findByDev(Long id);
    void delete(Long developmentId, Long id);

}
