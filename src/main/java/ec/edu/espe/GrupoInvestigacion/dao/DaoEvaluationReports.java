package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelEvaluationReports;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoEvaluationReports extends CrudRepository<ModelEvaluationReports, Long> {

    @Query("SELECT e FROM ModelEvaluationReports e")
    Optional<List<ModelEvaluationReports>> findAllEnable();

    @Query("SELECT e FROM ModelEvaluationReports e WHERE e.id = :id")
    Optional<ModelEvaluationReports> findByIdEnable(@Param("id") Long id);
}
