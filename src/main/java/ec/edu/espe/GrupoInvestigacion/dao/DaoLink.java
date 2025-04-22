package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelLink;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoLink extends CrudRepository<ModelLink, Long> {
    @Query(value = "SELECT l FROM ModelLink l")
    public Optional<List<ModelLink>> findAllEnable();
    @Query(value = "SELECT l FROM ModelLink l WHERE l.id = :id")
    Optional<ModelLink> findByIdEnable(@Param("id") Long id);
    @Query(value = "SELECT l FROM ModelLink l WHERE l.state = :estado")
    Optional<List<ModelLink>> findByState(@Param("estado") Character estado);

    @Query(value="SELECT l FROM ModelLink l WHERE l.modelInvGroup.id= :id AND l.state=: estado AND l.type=: tipo")
    Optional<ModelLink> findAllByGroup(@Param("id")Long id,@Param("estado")Character estado,@Param("tipo")Character tipo);
}
