package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelDislink;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoDislink  extends CrudRepository<ModelDislink, Long> {
    @Query(value = "SELECT dl FROM ModelDislink dl")
    public Optional<List<ModelDislink>> findAllEnable();
    @Query(value = "SELECT dl FROM ModelDislink dl WHERE dl.id = :id")
    Optional<ModelDislink> findByIdEnable(@Param("id") Long id);
}
