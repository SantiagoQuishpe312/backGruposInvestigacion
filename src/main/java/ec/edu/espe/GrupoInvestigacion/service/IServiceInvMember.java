package ec.edu.espe.GrupoInvestigacion.service;


import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvMember;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUser;

import java.util.List;

public interface IServiceInvMember {
    public List<DtoInvMember> findAll();

    public DtoInvGroup findByUsername(String id);

    public List<DtoInvMember> find(Long id);
    public List<DtoInvMember> findByUserNameInvMember(String username);
    public List<DtoInvMember> findGroup(Long id);
    public List<DtoUser> findInfoMembersByGroup(Long id);
    public DtoInvMember findExact(Long idUser, Long idGrupo);
    void deleteUser(Long userId, Long groupId);

    Long save(DtoInvMember dtoInvMember);
    void update(DtoInvMember dtoInvMember);
}
