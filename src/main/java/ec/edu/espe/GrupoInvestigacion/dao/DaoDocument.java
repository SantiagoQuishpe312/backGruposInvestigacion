package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelDocument;
import ec.edu.espe.GrupoInvestigacion.model.ModelEvents;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoDocument extends CrudRepository <ModelDocument,Long>{
    @Query(value = "SELECT d FROM ModelDocument d")
    public Optional<List<ModelDocument>> findAllEnable();
    @Query(value = "SELECT d FROM ModelDocument d WHERE d.id = :id")
    Optional<ModelDocument> findByIdEnable(@Param("id") Long id);
}
