package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoEvidences;
import ec.edu.espe.GrupoInvestigacion.dto.DtoEvidences;
import ec.edu.espe.GrupoInvestigacion.mapper.EvidencesMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelEvidences;
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
public class ServiceEvidences implements IServiceEvidences {

    @Autowired
    private DaoEvidences daoEvidences;

    @Autowired
    private EvidencesMapper evidencesMapper;

    @Override
    public DtoEvidences find(Long id) {
        return evidencesMapper.toDto(daoEvidences.findByIdEnable(id).orElse(new ModelEvidences()));
    }

    @Override
    public List<DtoEvidences> findAll() {
        return daoEvidences.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(evidencesMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(DtoEvidences dtoEvidences) {
        ModelEvidences modelEvidences = evidencesMapper.toEntity(dtoEvidences);
        ModelEvidences createdEntity = daoEvidences.save(modelEvidences);
        return createdEntity.getId();
    }

    @Override
    public void update(DtoEvidences dtoEvidences) {
        ModelEvidences existingEntity = daoEvidences.findByIdEnable(dtoEvidences.getId()).orElse(null);
        if (existingEntity != null) {
            ModelEvidences updatedEntity = evidencesMapper.toEntity(dtoEvidences);
            BeanUtils.copyProperties(updatedEntity, existingEntity, getNullPropertyNames(updatedEntity));
            daoEvidences.save(existingEntity);
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
