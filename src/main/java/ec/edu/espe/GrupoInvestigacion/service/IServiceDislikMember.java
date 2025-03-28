package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDislinkMember;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUser;

import java.util.List;

public interface IServiceDislikMember {
    public  List<DtoDislinkMember> findAll();

    public DtoInvGroup findByUsername(String id);

    public List<DtoDislinkMember> find(Long id);
    public List<DtoDislinkMember> findByUserNameInvMember(String username);
    public List<DtoDislinkMember> findGroup(Long id);
    public List<DtoUser> findInfoMembersByGroup(Long id);
    void deleteUser(Long userId, Long groupId);

    Long save(DtoDislinkMember dtoDislinkMember);
}
