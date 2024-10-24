package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelObjectives;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoObjectives extends JpaRepository<ModelObjectives, Long> {
    @Query(value = "SELECT o FROM ModelObjectives o")
    Optional<List<ModelObjectives>> findAllEnable();

    @Query(value = "SELECT o FROM ModelObjectives o WHERE o.id = :id")
    Optional<ModelObjectives> findByIdEnable(@Param("id") Long id);

    @Query(value = "SELECT o FROM ModelObjectives o WHERE o.modelDevelopmentPlan.id = :id")
    Optional<List<ModelObjectives>> findByDev(@Param("id") Long id);

}
