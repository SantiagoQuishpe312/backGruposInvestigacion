package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoDislinkMember;
import ec.edu.espe.GrupoInvestigacion.dao.DaoInvGroup;
import ec.edu.espe.GrupoInvestigacion.dao.DaoUser;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDislinkMember;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUser;
import ec.edu.espe.GrupoInvestigacion.mapper.DislinkMemberMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.InvGroupMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.UserMapper;
import ec.edu.espe.GrupoInvestigacion.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ServiceDislinkMember implements IServiceDislikMember {
    @Autowired
    private DaoDislinkMember daoDislinkMember;
    @Autowired
    private final DislinkMemberMapper dislinkMemberMapper;

    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private DaoUser daoUser;
    @Autowired
    private DaoInvGroup daoInvGroup;

    @Autowired
    private InvGroupMapper invGroupMapper;



    @Autowired
    public ServiceDislinkMember(DaoDislinkMember daoDislinkMember, DislinkMemberMapper dislinkMemberMapper, UserMapper userMapper) {
        this.daoDislinkMember = daoDislinkMember;
        this.dislinkMemberMapper = dislinkMemberMapper;
        this.userMapper=userMapper;
    }

    @Override
    public List<DtoDislinkMember> find(Long id) {
        return daoDislinkMember.findByIdEnable(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(dislinkMemberMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<DtoDislinkMember> findGroup(Long id){
        return daoDislinkMember.findByGroup(id).
                orElse(new ArrayList<>()).
                stream().
                map(dislinkMemberMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<DtoUser> findInfoMembersByGroup(Long id) {

        return daoDislinkMember.findMembersInfoByGroup(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoDislinkMember> findAll() {
        return daoDislinkMember.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(dislinkMemberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoDislinkMember> findByUserNameInvMember(String username) {
        return daoDislinkMember.findByUsernameInvMember(username)
                .orElse(new ArrayList<>())
                .stream()
                .map(dislinkMemberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoInvGroup findByUsername(String username) {
        Optional<ModelDislinkMember> modelInvGroup = daoDislinkMember.findByUsername(username);
        DtoInvGroup dtoInvGroup = new DtoInvGroup();

        // Verificar si el Optional tiene un valor
        if (modelInvGroup.isPresent()) {
            // Si tiene valor, setear los atributos en el DTO
            dtoInvGroup.setNombreGrupoInv(modelInvGroup.get().getModelInvGroup().getName());
            dtoInvGroup.setIdGrupoInv(modelInvGroup.get().getModelInvGroup().getId());
            dtoInvGroup.setVision(modelInvGroup.get().getModelInvGroup().getVision());
            dtoInvGroup.setMision(modelInvGroup.get().getModelInvGroup().getMission());
            dtoInvGroup.setIdCoordinador(modelInvGroup.get().getModelUser().getIdUser());
            dtoInvGroup.setAcronimoGrupoinv(modelInvGroup.get().getModelInvGroup().getAcronym());
            dtoInvGroup.setEstadoGrupoInv(modelInvGroup.get().getModelInvGroup().getState());
        } else {
            // Si no tiene valor, puedes manejarlo de una manera apropiada
            // Por ejemplo, puedes lanzar una excepción personalizada o devolver un DTO vacío.
            throw new EntityNotFoundException("El miembro con el username " + username + " no fue encontrado.");
        }

        return dtoInvGroup;
    }


    @Override
    public void deleteUser(Long userId, Long groupId) {
        Optional<ModelDislinkMember> invMemberOptional = daoDislinkMember.findByuserGroup(userId,groupId);
        if (invMemberOptional.isPresent()) {
            daoDislinkMember.delete(invMemberOptional.get());
        } else {
            throw new RuntimeException("No se encontró el grupo para el usuario");
        }
    }
    @Override
    public Long save(DtoDislinkMember dtoDislinkMember) {
        Long userId = dtoDislinkMember.getIdUsuario();
        Long groupId = dtoDislinkMember.getIdGrupoInv();
        ModelUser modelUser = daoUser.findById(userId).orElse(new ModelUser());
        ModelInvGroup modelInvGroup = daoInvGroup.findById(groupId).orElse(new ModelInvGroup());
        ModelDislinkMember modelDislinkMember = dislinkMemberMapper.toEntity(dtoDislinkMember);
        ModelDislinkMemberId id = new ModelDislinkMemberId();
        id.setModelUser(modelUser);
        id.setModelInvGroup(modelInvGroup);
        modelDislinkMember.setId(id);
        daoDislinkMember.save(modelDislinkMember);
        return modelUser.getIdUser();
    }
}
