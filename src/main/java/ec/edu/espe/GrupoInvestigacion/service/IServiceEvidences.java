package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoEvidences;
import java.util.List;

public interface IServiceEvidences {
    List<DtoEvidences> findAll();
    DtoEvidences find(Long id);
    Long save(DtoEvidences dtoEvidences);
    void update(DtoEvidences dtoEvidences);
}
