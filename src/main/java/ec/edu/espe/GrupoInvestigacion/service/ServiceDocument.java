package ec.edu.espe.GrupoInvestigacion.service;
import ec.edu.espe.GrupoInvestigacion.dao.DaoDocument;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDocument;
import ec.edu.espe.GrupoInvestigacion.mapper.DocumentMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelDocument;
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
public class ServiceDocument implements IServiceDocument {
    @Autowired
    private DaoDocument daoDocument;

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public DtoDocument find (Long id){
        return documentMapper.toDto(daoDocument.findByIdEnable(id).orElse(new ModelDocument()));
    }

    @Override
    public List<DtoDocument> findAll() {
        return daoDocument.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(DtoDocument dtoDocument){
        ModelDocument modelDocument=documentMapper.toEntity(dtoDocument);
        ModelDocument createdEntity=daoDocument.save(modelDocument);
        return createdEntity.getId();
    }

    @Override
    public void update(DtoDocument dtoDocument){
        ModelDocument existingEntity=daoDocument.findByIdEnable(dtoDocument.getIdDocument()).orElse(null);
        if(existingEntity!=null){
            ModelDocument updatedEntity=documentMapper.toEntity(dtoDocument);
            BeanUtils.copyProperties(updatedEntity,existingEntity,getNullPropertyNames(updatedEntity));
            daoDocument.save(existingEntity);
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
