package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoClosure;
import java.util.List;

public interface IServiceClosure {
    List<DtoClosure> findAll();
    DtoClosure find(Long id);
    Long save(DtoClosure dtoClosure);
    void update(DtoClosure dtoClosure);
}
