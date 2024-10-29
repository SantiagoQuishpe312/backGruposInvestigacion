package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelSpecificObjectives;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoSpecificObjectives extends JpaRepository<ModelSpecificObjectives, Long> {
    @Query(value = "SELECT o FROM ModelSpecificObjectives o")
    Optional<List<ModelSpecificObjectives>> findAllEnable();

    @Query(value = "SELECT o FROM ModelSpecificObjectives o WHERE o.id = :id")
    Optional<ModelSpecificObjectives> findByIdEnable(@Param("id") Long id);



}
