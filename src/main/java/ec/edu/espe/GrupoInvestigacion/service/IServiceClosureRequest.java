package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoClosureRequest;
import java.util.List;

public interface IServiceClosureRequest {
    List<DtoClosureRequest> findAll();
    DtoClosureRequest find(Long id);
    Long save(DtoClosureRequest dtoClosureRequest);
    void update(DtoClosureRequest dtoClosureRequest);
}
