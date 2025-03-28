package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelDislinkMember;
import ec.edu.espe.GrupoInvestigacion.model.ModelUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DaoDislinkMember extends CrudRepository<ModelDislinkMember, Long> {
    @Query(value = "SELECT mig FROM ModelDislinkMember mig")
    public Optional<List<ModelDislinkMember>> findAllEnable();

    @Query(value = "SELECT mig FROM ModelDislinkMember mig WHERE mig.modelUser.idUser =:id")
    public Optional<List<ModelDislinkMember>> findByIdEnable(Long id);

    @Query(value = "SELECT mig FROM ModelDislinkMember mig WHERE mig.modelInvGroup.id =:id")
    public Optional<List<ModelDislinkMember>> findByGroup(Long id);

    @Query(value = "SELECT mig FROM ModelDislinkMember mig WHERE mig.modelUser.idUser =:idUser and mig.modelInvGroup.id=:idGroup")
    public Optional<ModelDislinkMember> findByuserGroup(Long idUser, Long idGroup);

    @Query(value = "FROM ModelDislinkMember mig WHERE mig.modelUser.user =?1")
    public Optional<ModelDislinkMember> findByUsername(String username);

    @Query(value = "SELECT mig.modelUser FROM ModelDislinkMember mig WHERE mig.modelInvGroup.id =:id")
    public Optional<List<ModelUser>> findMembersInfoByGroup(Long id);

    @Query(value = "FROM ModelDislinkMember mig WHERE mig.modelUser.user =?1")
    public Optional<List<ModelDislinkMember>> findByUsernameInvMember(String username);
}
