package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoEvaluationsReport;
import java.util.List;

public interface IServiceEvaluationReports {
    List<DtoEvaluationsReport> findAll();
    DtoEvaluationsReport find(Long id);
    Long save(DtoEvaluationsReport dtoEvaluationsReport);
    void update(DtoEvaluationsReport dtoEvaluationsReport);
}
