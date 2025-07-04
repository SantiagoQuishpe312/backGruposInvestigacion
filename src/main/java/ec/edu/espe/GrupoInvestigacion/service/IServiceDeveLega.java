package ec.edu.espe.GrupoInvestigacion.service;


import ec.edu.espe.GrupoInvestigacion.dto.DtoDevGetLegalFramework;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDevGetNationalPlan;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDeveLega;
import ec.edu.espe.GrupoInvestigacion.dto.DtoLegalFramework;

import java.util.List;

public interface IServiceDeveLega {
    public List <DtoDeveLega> findAll();
    public List<DtoDeveLega> find(Long id);
    Long save(DtoDeveLega dtoDeveLega);
    public List<DtoLegalFramework> findByDev(Long id);
    void delete(Long developmentId, Long id);

}
