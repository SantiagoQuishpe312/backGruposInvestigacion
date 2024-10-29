package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelOds;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DaoOds extends CrudRepository<ModelOds,Long> {
    @Query(value ="SELECT ods FROM ModelOds ods"  )
    public Optional<List<ModelOds>> findAllEnable();

    @Query(value ="SELECT ods FROM ModelOds ods WHERE ods.id=:id"  )
    public Optional<ModelOds> findByIdEnable(Long id);
}
