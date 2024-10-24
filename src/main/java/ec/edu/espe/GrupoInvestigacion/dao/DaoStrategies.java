package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelObjectives;
import ec.edu.espe.GrupoInvestigacion.model.ModelStrategies;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoStrategies extends CrudRepository<ModelStrategies, Long> {

    @Query(value = "SELECT ms FROM ModelStrategies ms")
    Optional<List<ModelStrategies>> findAllEnable();

    @Query(value = "SELECT ms FROM ModelStrategies ms WHERE ms.id = :id")
    Optional<ModelStrategies> findByIdEnable(@Param("id") Long id);

    @Query(value = "SELECT ms FROM ModelStrategies ms WHERE ms.modelObjectives.id = :id")
    Optional<List<ModelStrategies>>findByObjective(@Param("id") Long id);

    @Query(value = "SELECT mo.strategies FROM ModelObjectives mo WHERE mo.modelDevelopmentPlan.id = :idPlan")
    public Optional<List<ModelStrategies>> findComplete(@Param("idPlan") Long idPlan);



}
