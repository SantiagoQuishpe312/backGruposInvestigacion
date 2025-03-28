package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoUnsatisfactory;
import java.util.List;

public interface IServiceUnsatisfactory {
    List<DtoUnsatisfactory> findAll();
    DtoUnsatisfactory find(Long id);
    Long save(DtoUnsatisfactory dtoUnsatisfactory);
    void update(DtoUnsatisfactory dtoUnsatisfactory);
}
