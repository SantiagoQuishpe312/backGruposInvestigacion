package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelInvGroup;
import ec.edu.espe.GrupoInvestigacion.model.ModelUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoInvGroup extends CrudRepository<ModelInvGroup, Long> {
    @Query(value = "SELECT mig FROM ModelInvGroup mig")
    public Optional<List<ModelInvGroup>> findAllEnable();

    @Query(value = "SELECT mig FROM ModelInvGroup mig WHERE mig.id =:id")
    public Optional<ModelInvGroup> findByIdEnable(Long id);
    @Query(value = "SELECT mig FROM ModelInvGroup mig WHERE mig.modelUser.idUser =:id")
    public Optional<ModelInvGroup> findByIdUser(Long id);

    @Query(value = "SELECT mig FROM ModelInvGroup mig where mig.process=:process")
    public Optional<List<ModelInvGroup>> findByProcess(@Param("process") String process);

    @Query(value = "SELECT mig FROM ModelInvGroup mig where mig.process=:process and mig.department=:department")
    public Optional<List<ModelInvGroup>> findByProcessAndDepartment(@Param("process") String process, @Param("department") String department);


}
