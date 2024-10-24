package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoDislink;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDislink;
import ec.edu.espe.GrupoInvestigacion.mapper.DislinkMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelDislink;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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
public class ServiceDislink implements IServiceDislink{
    @Autowired
    private DaoDislink daoDislink;
    @Autowired
    private DislinkMapper dislinkMapper;
    @Override
    public DtoDislink find(Long id){
        return dislinkMapper.toDto(daoDislink.findByIdEnable(id).orElse(new ModelDislink()));
    }
    @Override
public List<DtoDislink> findAll(){
        return daoDislink.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(dislinkMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public Long save(DtoDislink dtoDislink){
        ModelDislink modelDislink=dislinkMapper.toEntity(dtoDislink);
        ModelDislink createdEntity=daoDislink.save(modelDislink);
        return createdEntity.getId();
    }

    @Override
    public void update(DtoDislink dtoDislink){
        ModelDislink existingEntity=daoDislink.findByIdEnable(dtoDislink.getIdDesvinculacion()).orElse(null);
        if(existingEntity!=null){
            ModelDislink updatedEntity=dislinkMapper.toEntity(dtoDislink);
            BeanUtils.copyProperties(updatedEntity,existingEntity,getNullPropertyNames(updatedEntity));
            daoDislink.save(existingEntity);
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
