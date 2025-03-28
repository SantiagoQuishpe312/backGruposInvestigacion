package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoUnsatisfactory;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUnsatisfactory;
import ec.edu.espe.GrupoInvestigacion.mapper.UnsatisfactoryMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelUnsatisfactory;
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
public class ServiceUnsatisfactory implements IServiceUnsatisfactory {

    @Autowired
    private DaoUnsatisfactory daoUnsatisfactory;

    @Autowired
    private UnsatisfactoryMapper unsatisfactoryMapper;

    @Override
    public DtoUnsatisfactory find(Long id) {
        return unsatisfactoryMapper.toDto(daoUnsatisfactory.findByIdEnable(id).orElse(new ModelUnsatisfactory()));
    }

    @Override
    public List<DtoUnsatisfactory> findAll() {
        return daoUnsatisfactory.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(unsatisfactoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(DtoUnsatisfactory dtoUnsatisfactory) {
        ModelUnsatisfactory modelUnsatisfactory = unsatisfactoryMapper.toEntity(dtoUnsatisfactory);
        ModelUnsatisfactory createdEntity = daoUnsatisfactory.save(modelUnsatisfactory);
        return createdEntity.getId();
    }

    @Override
    public void update(DtoUnsatisfactory dtoUnsatisfactory) {
        ModelUnsatisfactory existingEntity = daoUnsatisfactory.findByIdEnable(dtoUnsatisfactory.getId()).orElse(null);
        if (existingEntity != null) {
            ModelUnsatisfactory updatedEntity = unsatisfactoryMapper.toEntity(dtoUnsatisfactory);
            BeanUtils.copyProperties(updatedEntity, existingEntity, getNullPropertyNames(updatedEntity));
            daoUnsatisfactory.save(existingEntity);
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
