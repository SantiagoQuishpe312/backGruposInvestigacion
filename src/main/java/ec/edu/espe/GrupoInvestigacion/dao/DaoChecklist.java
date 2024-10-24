package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelChecklist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DaoChecklist extends CrudRepository<ModelChecklist, Long> {
    @Query(value = "SELECT mchk FROM ModelChecklist mchk")
    public Optional<List<ModelChecklist>> findAllEnable();

    @Query(value = "SELECT mchk FROM ModelChecklist mchk WHERE mchk.id =:id")
    public Optional<ModelChecklist> findByIdEnable(Long id);
    @Query(value = "SELECT mchk FROM ModelChecklist mchk WHERE mchk.modelInvGroup.id =:id")
    public Optional<ModelChecklist> findByGroup(Long id);
}
