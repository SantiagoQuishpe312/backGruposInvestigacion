package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelClosure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoClosure extends CrudRepository<ModelClosure, Long> {

    @Query("SELECT c FROM ModelClosure c")
    Optional<List<ModelClosure>> findAllEnable();

    @Query("SELECT c FROM ModelClosure c WHERE c.id = :id")
    Optional<ModelClosure> findByIdEnable(@Param("id") Long id);

    @Query("SELECT c from ModelClosure c WHERE c.modelInvGroup.id= :id")
    Optional<ModelClosure> findByGroup(@Param("id")Long id);
}
