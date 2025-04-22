package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoArea;
import ec.edu.espe.GrupoInvestigacion.dao.DaoInvGroup;
import ec.edu.espe.GrupoInvestigacion.dao.DaoLink;
import ec.edu.espe.GrupoInvestigacion.dao.DaoUser;
import ec.edu.espe.GrupoInvestigacion.dto.DtoLink;
import ec.edu.espe.GrupoInvestigacion.dto.DtoLinkGetData;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUser;
import ec.edu.espe.GrupoInvestigacion.mapper.AreaMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.InvGroupMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.LinkMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.UserMapper;
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
public class ServiceLink implements IServiceLink {
    @Autowired
    private DaoLink daoLink;
    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoInvGroup daoInvGroup;

    @Autowired
    private DaoArea daoArea;

    @Autowired
    private IServiceInvGroup serviceInvGroup;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private InvGroupMapper invGroupMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public DtoLink find (Long id){
        return linkMapper.toDto(daoLink.findByIdEnable(id).orElse(new ModelLink()));
    }
    @Override
    public List<DtoLink> findAll(){
        return daoLink.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(linkMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<DtoLink> findByState(Character estado){
        return daoLink.findByState(estado)
                .orElse(new ArrayList<>())
                .stream()
                .map(linkMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoLinkGetData findAllByGroup(Long id,String tipo,Character estado){
        Optional<ModelLink> modelLink = daoLink.findAllByGroup(id, estado, tipo);
        if(modelLink.isEmpty()){
            throw new NoSuchElementException("");
        }
        Optional<ModelUser> modelUser=daoUser.findByIdEnable(modelLink.get().getId());
        DtoLinkGetData dto=new DtoLinkGetData();
        dto.setFormularioVinculacion(modelLink.map(linkMapper::toDto).orElse(new DtoLink()));
        dto.setGrupoInvestigacion(serviceInvGroup.findAllData(id));
        dto.setNuevoInvestigador(modelUser.map(userMapper::toDto).orElse(new DtoUser()));
        return dto;
    }
    @Override
    public Long save(DtoLink dtoLink){
        ModelLink modelLink=linkMapper.toEntity(dtoLink);
        ModelLink createdEntity=daoLink.save(modelLink);
        return createdEntity.getId();
    }

    @Override
    public void update(DtoLink dtoLink){
        ModelLink existingEntity=daoLink.findByIdEnable(dtoLink.getIdVinculacion()).orElse(null);
        if(existingEntity!=null){
            ModelLink updatedEntity=linkMapper.toEntity(dtoLink);
            BeanUtils.copyProperties(updatedEntity,existingEntity,getNullPropertyNames(updatedEntity));
            daoLink.save(existingEntity);
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
