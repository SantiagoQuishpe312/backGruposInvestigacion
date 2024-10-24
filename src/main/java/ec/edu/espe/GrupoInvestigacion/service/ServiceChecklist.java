package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoChecklist;
import ec.edu.espe.GrupoInvestigacion.dao.DaoUser;

import ec.edu.espe.GrupoInvestigacion.dto.DtoChecklist;

import ec.edu.espe.GrupoInvestigacion.dto.DtoGroupRegForm;
import ec.edu.espe.GrupoInvestigacion.model.ModelChecklist;
import ec.edu.espe.GrupoInvestigacion.mapper.ChecklistMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelGroupRegForm;
import ec.edu.espe.GrupoInvestigacion.model.ModelUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceChecklist implements IServiceChecklist {

    @Autowired
    private DaoChecklist daoChecklist;


    @Autowired
    private ChecklistMapper checklistMapper;


    @Override
    public DtoChecklist find(Long id) {
        return checklistMapper.toDto(daoChecklist.findByIdEnable(id).orElse(new ModelChecklist()));

    }
    @Override
    public DtoChecklist findByGroup(Long id) {
        return checklistMapper.toDto(daoChecklist.findByGroup(id).orElse(new ModelChecklist()));

    }


    @Override
    public List<DtoChecklist> findAll() {
        return daoChecklist.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(checklistMapper::toDto)
                .collect(Collectors.toList());

    }
    @Override

    public Long save(DtoChecklist dtoChecklist) {
        ModelChecklist modelChecklist = checklistMapper.toEntity(dtoChecklist);
        ModelChecklist createdEntity = daoChecklist.save(modelChecklist);

        return createdEntity.getId();
    }

    public void update(DtoChecklist dtoChecklist){
        ModelChecklist existingEntity=daoChecklist.findByIdEnable(dtoChecklist.getIdChecklist()).orElse(null);
        if(existingEntity!=null){
            ModelChecklist updatedEntity=checklistMapper.toEntity(dtoChecklist);
            BeanUtils.copyProperties(updatedEntity,existingEntity,getNullPropertyNames(updatedEntity));
            daoChecklist.save(existingEntity);
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



