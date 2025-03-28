package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelEvidences;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoEvidences extends CrudRepository<ModelEvidences, Long> {

    @Query("SELECT e FROM ModelEvidences e")
    Optional<List<ModelEvidences>> findAllEnable();

    @Query("SELECT e FROM ModelEvidences e WHERE e.id = :id")
    Optional<ModelEvidences> findByIdEnable(@Param("id") Long id);
}
