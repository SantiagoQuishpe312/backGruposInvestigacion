package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoClosureRequest;
import ec.edu.espe.GrupoInvestigacion.dto.DtoClosureRequest;
import ec.edu.espe.GrupoInvestigacion.mapper.ClosureRequestMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelClosureRequest;
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
public class ServiceClosureRequest implements IServiceClosureRequest {

    @Autowired
    private DaoClosureRequest daoClosureRequest;

    @Autowired
    private ClosureRequestMapper closureRequestMapper;

    @Override
    public DtoClosureRequest find(Long id) {
        return closureRequestMapper.toDto(daoClosureRequest.findByIdEnable(id).orElse(new ModelClosureRequest()));
    }

    @Override
    public List<DtoClosureRequest> findAll() {
        return daoClosureRequest.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(closureRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(DtoClosureRequest dtoClosureRequest) {
        ModelClosureRequest modelClosureRequest = closureRequestMapper.toEntity(dtoClosureRequest);
        ModelClosureRequest createdEntity = daoClosureRequest.save(modelClosureRequest);
        return createdEntity.getId();
    }

    @Override
    public void update(DtoClosureRequest dtoClosureRequest) {
        ModelClosureRequest existingEntity = daoClosureRequest.findByIdEnable(dtoClosureRequest.getId()).orElse(null);
        if (existingEntity != null) {
            ModelClosureRequest updatedEntity = closureRequestMapper.toEntity(dtoClosureRequest);
            BeanUtils.copyProperties(updatedEntity, existingEntity, getNullPropertyNames(updatedEntity));
            daoClosureRequest.save(existingEntity);
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
