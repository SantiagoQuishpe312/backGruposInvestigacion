package ec.edu.espe.GrupoInvestigacion.service;


import ec.edu.espe.GrupoInvestigacion.dao.DaoActivityReport;
import ec.edu.espe.GrupoInvestigacion.dao.DaoExhibit;
import ec.edu.espe.GrupoInvestigacion.dto.DtoExhibit;
import ec.edu.espe.GrupoInvestigacion.dto.DtoGroupRegForm;
import ec.edu.espe.GrupoInvestigacion.mapper.ExhibitMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelExhibit;
import ec.edu.espe.GrupoInvestigacion.model.ModelGroupRegForm;
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
public class ServiceExhibit implements IServiceExhibit{
    @Autowired
    private DaoExhibit daoExhibit;

    @Autowired
    private ExhibitMapper exhibitMapper;

    @Override
    public DtoExhibit find(Long id) {
        return exhibitMapper.toDto(daoExhibit.findByIdEnable(id).orElse(new ModelExhibit()));
    }

    @Override
    public List<DtoExhibit> findAll() {
        return daoExhibit.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(exhibitMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long create(DtoExhibit dtoExhibit) {
        ModelExhibit modelExhibit = exhibitMapper.toEntity(dtoExhibit);
        ModelExhibit createdEntity = daoExhibit.save(modelExhibit);
        return createdEntity.getId();
    }

    @Override
    public void update(DtoExhibit dtoExhibit){
        ModelExhibit existingEntity= daoExhibit.findByIdEnable(dtoExhibit.getIdExhibit()).orElse(null);
        if(existingEntity!=null){
            ModelExhibit updatedEntity=exhibitMapper.toEntity(dtoExhibit);
            BeanUtils.copyProperties(updatedEntity,existingEntity,getNullPropertyNames(updatedEntity));
            daoExhibit.save(existingEntity);
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
