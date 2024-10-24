package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoAcademicDomain;
import ec.edu.espe.GrupoInvestigacion.dao.DaoCreationReq;
import ec.edu.espe.GrupoInvestigacion.dao.DaoInvGroup;
import ec.edu.espe.GrupoInvestigacion.dao.DaoUser;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup;
import ec.edu.espe.GrupoInvestigacion.mapper.InvGroupMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelCreationReq;
import ec.edu.espe.GrupoInvestigacion.model.ModelExhibit;
import ec.edu.espe.GrupoInvestigacion.model.ModelInvGroup;
import ec.edu.espe.GrupoInvestigacion.model.ModelUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceInvGroup implements IServiceInvGroup {
    @Autowired
    private DaoInvGroup daoInvGroup;
    @Autowired
    private DaoUser daoUser;
    @Autowired
    private DaoCreationReq daoCreationReq;
    @Autowired
    private InvGroupMapper invGroupMapper;

    @Autowired
    private DaoAcademicDomain daoAcademicDomain;
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
}
