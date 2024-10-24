package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelExhibit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DaoExhibit extends CrudRepository<ModelExhibit, Long> {
    @Query(value = "SELECT me FROM ModelExhibit me")
    public Optional<List<ModelExhibit>> findAllEnable();

    @Query(value = "SELECT me FROM ModelExhibit me WHERE me.modelCreationReq.id =:id")
    public Optional<ModelExhibit> findByIdEnable(Long id);
}
