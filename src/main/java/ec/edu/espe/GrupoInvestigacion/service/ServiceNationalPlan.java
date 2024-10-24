package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoNationalPlan;
import ec.edu.espe.GrupoInvestigacion.dto.DtoNationalPlan;
import ec.edu.espe.GrupoInvestigacion.mapper.NationalPlanMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelNationalPlan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
public class ServiceNationalPlan implements IServiceNationalPlan {

    @Autowired
    private DaoNationalPlan daoNationalPlan;

    @Autowired
    private NationalPlanMapper nationalPlanMapper;

    @Override
    public DtoNationalPlan find(Long id) {
        return nationalPlanMapper.toDto(daoNationalPlan.findByIdEnable(id).orElse(new ModelNationalPlan()));
    }

    @Override
    public List<DtoNationalPlan> findAll() {
        return daoNationalPlan.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(nationalPlanMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(DtoNationalPlan dto) {
        ModelNationalPlan model = nationalPlanMapper.toEntity(dto);
        ModelNationalPlan createdEntity = daoNationalPlan.save(model);
        return createdEntity.getId();
    }
    @Override
    public void update(DtoNationalPlan dtoNationalPlan) {
        ModelNationalPlan existingEntity = daoNationalPlan.findById(dtoNationalPlan.getIdPlanNacional()).orElse(null);
          if(existingEntity!=null){
              ModelNationalPlan updatedEntity=nationalPlanMapper.toEntity(dtoNationalPlan);
              BeanUtils.copyProperties(updatedEntity,existingEntity,getNullPropertyNames(updatedEntity));
              daoNationalPlan.save(existingEntity);
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
