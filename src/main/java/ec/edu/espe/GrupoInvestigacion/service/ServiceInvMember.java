package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoInvGroup;
import ec.edu.espe.GrupoInvestigacion.dao.DaoInvMember;
import ec.edu.espe.GrupoInvestigacion.dao.DaoUser;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvMember;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvMemberGetGroup;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUser;
import ec.edu.espe.GrupoInvestigacion.mapper.InvGroupMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.InvMemberMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.UserMapper;
import ec.edu.espe.GrupoInvestigacion.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceInvMember implements IServiceInvMember {

    @Autowired
    private DaoInvMember daoInvMember;
    @Autowired
    private final InvMemberMapper invMemberMapper;

    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private DaoUser daoUser;
    @Autowired
    private DaoInvGroup daoInvGroup;

    @Autowired
    private InvGroupMapper invGroupMapper;



    @Autowired
    public ServiceInvMember(DaoInvMember daoInvMember, InvMemberMapper invMemberMapper, UserMapper userMapper) {
        this.daoInvMember = daoInvMember;
        this.invMemberMapper = invMemberMapper;
        this.userMapper=userMapper;
    }

    @Override
    public List<DtoInvMember> find(Long id) {
        return daoInvMember.findByIdEnable(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(invMemberMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<DtoInvMember> findGroup(Long id){
        return daoInvMember.findByGroup(id).
                orElse(new ArrayList<>()).
                stream().
                map(invMemberMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<DtoUser> findInfoMembersByGroup(Long id) {

        return daoInvMember.findMembersInfoByGroup(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoInvMember> findAll() {
        return daoInvMember.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(invMemberMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public DtoInvMember findExact(Long idUser, Long idGroup){
        return invMemberMapper.toDto(daoInvMember.findByuserGroup(idUser, idGroup).orElse(new ModelInvMember()));
    }

    @Override
    public List<DtoInvMember> findByUserNameInvMember(String username) {
        return daoInvMember.findByUsernameInvMember(username)
                .orElse(new ArrayList<>())
                .stream()
                .map(invMemberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoInvGroup findByUsername(String username) {
        Optional<ModelInvMember> modelInvGroup = daoInvMember.findByUsername(username);
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
        Optional<ModelInvMember> invMemberOptional = daoInvMember.findByuserGroup(userId,groupId);
        if (invMemberOptional.isPresent()) {
            daoInvMember.delete(invMemberOptional.get());
        } else {
            throw new RuntimeException("No se encontró el grupo para el usuario");
        }
    }

    public DtoInvMemberGetGroup getMiembro(Long userId) {
        Optional<List<ModelInvMember>> modelInvMembersOpt = daoInvMember.findByIdEnable(userId);
        DtoInvMemberGetGroup dto = new DtoInvMemberGetGroup();

        if (modelInvMembersOpt.isPresent()) {
            List<ModelInvMember> miembros = modelInvMembersOpt.get();
            Set<ModelInvGroup> grupos = new HashSet<>(); // para evitar duplicados

            for (ModelInvMember miembro : miembros) {
                ModelInvGroup grupo = miembro.getModelInvGroup();
                if (grupo != null) {
                    grupos.add(grupo);
                }
            }

            List<DtoInvGroup> gruposDto = grupos.stream()
                    .map(grupo -> {
                        DtoInvGroup dtoGroup = new DtoInvGroup();
                        dtoGroup.setIdGrupoInv(grupo.getId()); // Asume que ModelInvGroup tiene getId()
                        return dtoGroup;
                    })
                    .collect(Collectors.toList());

            dto.setGrupo(gruposDto);
        } else {
            dto.setGrupo(Collections.emptyList());
        }

        return dto;
    }

    @Override
    public Long save(DtoInvMember dtoInvMember) {
        Long userId = dtoInvMember.getIdUsuario();
        Long groupId = dtoInvMember.getIdGrupoInv();
        ModelUser modelUser = daoUser.findById(userId).orElse(new ModelUser());
        ModelInvGroup modelInvGroup = daoInvGroup.findById(groupId).orElse(new ModelInvGroup());
        ModelInvMember modelInvMember = invMemberMapper.toEntity(dtoInvMember);
        ModelInvMemberId id = new ModelInvMemberId();
        id.setModelUser(modelUser);
        id.setModelInvGroup(modelInvGroup);
        modelInvMember.setId(id);
        daoInvMember.save(modelInvMember);
        return modelUser.getIdUser();
    }


    @Override
    public void update(DtoInvMember dtoInvMember){
        ModelInvMember existingEntity=daoInvMember.findByuserGroup(dtoInvMember.getIdUsuario(), dtoInvMember.getIdGrupoInv()).orElse(null);
        if(existingEntity!=null){
            ModelInvMember updatedEntity=invMemberMapper.toEntity(dtoInvMember);
            BeanUtils.copyProperties(updatedEntity,existingEntity,getNullPropertyNames(updatedEntity));
            daoInvMember.save(existingEntity);
        }
    }
    private String[] getNullPropertyNames(Object source) {
        try {
            final BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
            final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            final Set<String> emptyNames = new HashSet<>();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                final Object propertyValue = propertyDescriptor.getReadMethod().invoke(source);
                if (propertyValue == null) {
                    emptyNames.add(propertyDescriptor.getName());
                }
            }
            String[] result = new String[emptyNames.size()];
            return emptyNames.toArray(result);
        } catch (Exception e) {
            return new String[0];
        }
    }
}
