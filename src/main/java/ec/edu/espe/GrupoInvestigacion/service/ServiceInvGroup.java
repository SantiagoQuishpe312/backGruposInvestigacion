package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.*;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroupGetData;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvMember;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUser;
import ec.edu.espe.GrupoInvestigacion.mapper.*;
import ec.edu.espe.GrupoInvestigacion.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceInvGroup implements IServiceInvGroup {
    @Autowired
    private DaoInvGroup daoInvGroup;
    @Autowired
    private DaoUser daoUser;
    @Autowired
    private DaoInvMember daoInvMember;
    @Autowired
    private DaoInvGroup_Area daoArea;
    @Autowired
    private DaoInvGroup_Line daoLine;

    @Autowired
    private DaoCreationReq daoCreationReq;
    @Autowired
    private InvGroupMapper invGroupMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private InvMemberMapper invMemberMapper;
    @Autowired
    private AcademicDomainMapper academicDomainMapper;
    @Autowired
    private LineMapper lineMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private DaoInvGroup_AcademicDomain daoAcademicDomain;
    @Autowired

    public ServiceInvGroup(DaoInvGroup daoInvGroup, DaoUser daoUser,InvGroupMapper invGroupMapper, DaoCreationReq daoCreationReq){
        this.daoInvGroup=daoInvGroup;
        this.daoUser=daoUser;
        this.invGroupMapper=invGroupMapper;
        this.daoCreationReq=daoCreationReq;
    }
    @Override
    public DtoInvGroup find(Long id) {
        return invGroupMapper.toDto(daoInvGroup.findByIdEnable(id).orElse(new ModelInvGroup()));
    }

    public DtoInvGroup findByUser(Long id){
        return invGroupMapper.toDto(daoInvGroup.findByIdUser(id).orElse(new ModelInvGroup()));
    }
    @Override
    public List<DtoInvGroup> findAll() {
        return daoInvGroup.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(invGroupMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public Long save(DtoInvGroup dtoInvGroup){
        ModelInvGroup modelInvGroup=invGroupMapper.toEntity(dtoInvGroup);
        ModelInvGroup createdEntity=daoInvGroup.save(modelInvGroup);
        return createdEntity.getId();
    }

    @Override
    public void update(DtoInvGroup dtoInvGroup) {
        ModelInvGroup existingEntity = daoInvGroup.findByIdEnable(dtoInvGroup.getIdGrupoInv()).orElse(null);
        if (existingEntity != null) {
            ModelInvGroup updatedEntity = invGroupMapper.toEntity(dtoInvGroup);
            ModelUser modelUser = daoUser.findByIdEnable(existingEntity.getModelUser().getIdUser()).orElse(null);
            System.out.println("No se encontró el usuario con ID: " + updatedEntity.getModelUser().getIdUser());

            if (modelUser != null) {
                ModelUser modelUse=daoUser.findByIdEnable(updatedEntity.getModelUser().getIdUser()).orElse(null);
                updatedEntity.setModelUser(modelUse);
            }

            BeanUtils.copyProperties(updatedEntity, existingEntity, getNullPropertyNames(updatedEntity));
            daoInvGroup.save(existingEntity);
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

    @Override
    public DtoInvGroupGetData findAllData(Long id) {
        Optional<ModelInvGroup> modelInvGroup = daoInvGroup.findByIdEnable(id);
        Optional<List<ModelInvMember>> modelInvMembers = daoInvMember.findByGroup(id);
        Optional<List<ModelArea>> modelAreas = daoArea.findArea(id);
        Optional<List<ModelAcademicDomain>> modelAcademicDomains = daoAcademicDomain.findAcademicDomain(id);
        Optional<List<ModelLine>> modelLines = daoLine.findLine(id);

        DtoInvGroupGetData dtoInvGroupGetData = new DtoInvGroupGetData();

        if (modelInvGroup.isPresent()) {
            dtoInvGroupGetData.setInvGroup(modelInvGroup.map(invGroupMapper::toDto).orElse(new DtoInvGroup()));
            dtoInvGroupGetData.setLine(modelLines.orElse(new ArrayList<>()).stream().map(lineMapper::toDto).collect(Collectors.toList()));
            dtoInvGroupGetData.setArea(modelAreas.orElse(new ArrayList<>()).stream().map(areaMapper::toDto).collect(Collectors.toList()));
            dtoInvGroupGetData.setAcademicDomain(modelAcademicDomains.orElse(new ArrayList<>()).stream().map(academicDomainMapper::toDTO).collect(Collectors.toList()));

            // Convertimos los miembros y agregamos su información de usuario
            List<DtoInvMember> dtoMembers = modelInvMembers.orElse(new ArrayList<>()).stream().map(member -> {
                DtoInvMember dtoMember = invMemberMapper.toDto(member);
                daoUser.findByIdEnable(member.getModelUser().getIdUser()).ifPresent(user -> dtoMember.setUser(userMapper.toDto(user)));
                return dtoMember;
            }).collect(Collectors.toList());

            dtoInvGroupGetData.setUsers(dtoMembers);

            dtoInvGroupGetData.setCoordinador(daoUser.findByIdEnable(modelInvGroup.get().getModelUser().getIdUser())
                    .map(userMapper::toDto)
                    .orElse(new DtoUser()));

            return dtoInvGroupGetData;
        }

        throw new RuntimeException("No se encontraron datos");
    }

    @Override
    public List<DtoInvGroupGetData> findByProcess(String process) {
        Optional<List<ModelInvGroup>> modelInvGroup = daoInvGroup.findByProcess(process);
        List<DtoInvGroupGetData> list = new ArrayList<>();

        if (modelInvGroup.isPresent() && !modelInvGroup.get().isEmpty()) {
            for (ModelInvGroup invGroup : modelInvGroup.get()) {
                DtoInvGroupGetData dtoInvGroupGetData = new DtoInvGroupGetData();
                dtoInvGroupGetData.setInvGroup(invGroupMapper.toDto(invGroup));
                dtoInvGroupGetData.setCoordinador(daoUser.findByIdEnable(invGroup.getModelUser().getIdUser())
                        .map(userMapper::toDto)
                        .orElse(new DtoUser()));

                dtoInvGroupGetData.setLine(daoLine.findLine(invGroup.getId())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(lineMapper::toDto)
                        .collect(Collectors.toList()));

                dtoInvGroupGetData.setArea(daoArea.findArea(invGroup.getId())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(areaMapper::toDto)
                        .collect(Collectors.toList()));

                dtoInvGroupGetData.setAcademicDomain(daoAcademicDomain.findAcademicDomain(invGroup.getId())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(academicDomainMapper::toDTO)
                        .collect(Collectors.toList()));

                // Convertir miembros y agregar información del usuario
                List<DtoInvMember> dtoMembers = daoInvMember.findByGroup(invGroup.getId())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(member -> {
                            DtoInvMember dtoMember = invMemberMapper.toDto(member);
                            daoUser.findByIdEnable(member.getModelUser().getIdUser())
                                    .ifPresent(user -> dtoMember.setUser(userMapper.toDto(user)));
                            return dtoMember;
                        })
                        .collect(Collectors.toList());

                dtoInvGroupGetData.setUsers(dtoMembers);
                list.add(dtoInvGroupGetData);
            }
        }
        return list;
    }

    @Override
    public List<DtoInvGroupGetData> findByProcessAndDepartment(String process, String department) {
        Optional<List<ModelInvGroup>> modelInvGroup = daoInvGroup.findByProcessAndDepartment(process, department);
        List<DtoInvGroupGetData> list = new ArrayList<>();

        if (modelInvGroup.isPresent() && !modelInvGroup.get().isEmpty()) {
            for (ModelInvGroup invGroup : modelInvGroup.get()) {
                DtoInvGroupGetData dtoInvGroupGetData = new DtoInvGroupGetData();
                dtoInvGroupGetData.setInvGroup(invGroupMapper.toDto(invGroup));
                dtoInvGroupGetData.setCoordinador(daoUser.findByIdEnable(invGroup.getModelUser().getIdUser())
                        .map(userMapper::toDto)
                        .orElse(new DtoUser()));

                dtoInvGroupGetData.setLine(daoLine.findLine(invGroup.getId())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(lineMapper::toDto)
                        .collect(Collectors.toList()));

                dtoInvGroupGetData.setArea(daoArea.findArea(invGroup.getId())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(areaMapper::toDto)
                        .collect(Collectors.toList()));

                dtoInvGroupGetData.setAcademicDomain(daoAcademicDomain.findAcademicDomain(invGroup.getId())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(academicDomainMapper::toDTO)
                        .collect(Collectors.toList()));

                // Convertir miembros y agregar información del usuario
                List<DtoInvMember> dtoMembers = daoInvMember.findByGroup(invGroup.getId())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(member -> {
                            DtoInvMember dtoMember = invMemberMapper.toDto(member);
                            daoUser.findByIdEnable(member.getModelUser().getIdUser())
                                    .ifPresent(user -> dtoMember.setUser(userMapper.toDto(user)));
                            return dtoMember;
                        })
                        .collect(Collectors.toList());

                dtoInvGroupGetData.setUsers(dtoMembers);
                list.add(dtoInvGroupGetData);
            }
        }
        return list;
    }


}
