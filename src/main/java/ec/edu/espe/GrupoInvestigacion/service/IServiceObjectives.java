package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoObjectives;

import java.util.Date;
import java.util.List;

public interface IServiceObjectives {
    List<DtoObjectives> findAll();

    DtoObjectives find(Long id);
    List<DtoObjectives> findByDev(Long id);
    Long save(DtoObjectives dtoObjectives);
    void update(DtoObjectives dtoObjectives);
}
