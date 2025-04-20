package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelClosureRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoClosureRequest extends CrudRepository<ModelClosureRequest, Long> {

    @Query("SELECT c FROM ModelClosureRequest c")
    Optional<List<ModelClosureRequest>> findAllEnable();

    @Query("SELECT c FROM ModelClosureRequest c WHERE c.id = :id")
    Optional<ModelClosureRequest> findByIdEnable(@Param("id") Long id);

    @Query("SELECT c from ModelClosureRequest c where c.modelClosure.id= :id")
    Optional<ModelClosureRequest> findByClosure(@Param("id")Long id);
}
