package ec.edu.espe.GrupoInvestigacion.service;

import ch.qos.logback.core.model.Model;
import ec.edu.espe.GrupoInvestigacion.dao.DaoClosure;
import ec.edu.espe.GrupoInvestigacion.dao.DaoClosureRequest;
import ec.edu.espe.GrupoInvestigacion.dao.DaoUnsatisfactory;
import ec.edu.espe.GrupoInvestigacion.dto.DtoClosure;
import ec.edu.espe.GrupoInvestigacion.dto.DtoClosureGetAllData;
import ec.edu.espe.GrupoInvestigacion.dto.DtoClosureRequest;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUnsatisfactory;
import ec.edu.espe.GrupoInvestigacion.mapper.ClosureMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.ClosureRequestMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.UnsatisfactoryMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelClosure;
import ec.edu.espe.GrupoInvestigacion.model.ModelClosureRequest;
import ec.edu.espe.GrupoInvestigacion.model.ModelUnsatisfactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceClosure implements IServiceClosure {

    @Autowired
    private DaoClosure daoClosure;

    @Autowired
    private DaoClosureRequest daoClosureRequest;

    @Autowired
    private DaoUnsatisfactory daoUnsatisfactory;
    @Autowired
    private ClosureMapper closureMapper;
    @Autowired
    private ClosureRequestMapper closureRequesTMapper;
    @Autowired
    private UnsatisfactoryMapper unsatisfactoryMapper;

    @Override
    public DtoClosure find(Long id) {
        return closureMapper.toDto(daoClosure.findByIdEnable(id).orElse(new ModelClosure()));
    }

    @Override
    public List<DtoClosure> findAll() {
        return daoClosure.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(closureMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(DtoClosure dtoClosure) {
        ModelClosure modelClosure = closureMapper.toEntity(dtoClosure);
        ModelClosure createdEntity = daoClosure.save(modelClosure);
        return createdEntity.getId();
    }

    @Override
    public void update(DtoClosure dtoClosure) {
        ModelClosure existingEntity = daoClosure.findByIdEnable(dtoClosure.getId()).orElse(null);
        if (existingEntity != null) {
            ModelClosure updatedEntity = closureMapper.toEntity(dtoClosure);
            BeanUtils.copyProperties(updatedEntity, existingEntity, getNullPropertyNames(updatedEntity));
            daoClosure.save(existingEntity);
        }
    }
    @Override
    public DtoClosureGetAllData findByClosure(Long id) {
        Optional<ModelClosure> modelClosureOpt = daoClosure.findByGroup(id);

        if (modelClosureOpt.isEmpty()) {
            throw new RuntimeException("No se encontró cierre para el grupo con ID: " + id);
        }

        ModelClosure modelClosure = modelClosureOpt.get();
        Long closureId = modelClosure.getId();

        Optional<ModelClosureRequest> modelClosureRequestOpt = daoClosureRequest.findByClosure(closureId);
        Optional<ModelUnsatisfactory> modelUnsatisfactoryOpt = daoUnsatisfactory.findByClosure(closureId);

        DtoClosureGetAllData dtoClosureGetAllData = new DtoClosureGetAllData();
        dtoClosureGetAllData.setInformeCierre(closureMapper.toDto(modelClosure));
        dtoClosureGetAllData.setSolicitudCierreGI(modelClosureRequestOpt.map(closureRequesTMapper::toDto).orElse(new DtoClosureRequest()));
        dtoClosureGetAllData.setEvInsatisfactoria(modelUnsatisfactoryOpt.map(unsatisfactoryMapper::toDto).orElse(new DtoUnsatisfactory()));

        return dtoClosureGetAllData;
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
