package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelUnsatisfactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoUnsatisfactory extends CrudRepository<ModelUnsatisfactory, Long> {

    @Query("SELECT u FROM ModelUnsatisfactory u")
    Optional<List<ModelUnsatisfactory>> findAllEnable();

    @Query("SELECT u FROM ModelUnsatisfactory u WHERE u.id = :id")
    Optional<ModelUnsatisfactory> findByIdEnable(@Param("id") Long id);
}
