package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoEvaluationReports;
import ec.edu.espe.GrupoInvestigacion.dto.DtoEvaluationsReport;
import ec.edu.espe.GrupoInvestigacion.mapper.EvaluationReportsMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelEvaluationReports;
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
public class ServiceEvaluationReports implements IServiceEvaluationReports {

    @Autowired
    private DaoEvaluationReports daoEvaluationReports;

    @Autowired
    private EvaluationReportsMapper evaluationsReportMapper;

    @Override
    public DtoEvaluationsReport find(Long id) {
        return evaluationsReportMapper.toDto(daoEvaluationReports.findByIdEnable(id).orElse(new ModelEvaluationReports()));
    }

    @Override
    public List<DtoEvaluationsReport> findAll() {
        return daoEvaluationReports.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(evaluationsReportMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(DtoEvaluationsReport dtoEvaluationsReport) {
        ModelEvaluationReports modelEvaluationReports = evaluationsReportMapper.toEntity(dtoEvaluationsReport);
        ModelEvaluationReports createdEntity = daoEvaluationReports.save(modelEvaluationReports);
        return createdEntity.getId();
    }

    @Override
    public void update(DtoEvaluationsReport dtoEvaluationsReport) {
        ModelEvaluationReports existingEntity = daoEvaluationReports.findByIdEnable(dtoEvaluationsReport.getId()).orElse(null);
        if (existingEntity != null) {
            ModelEvaluationReports updatedEntity = evaluationsReportMapper.toEntity(dtoEvaluationsReport);
            BeanUtils.copyProperties(updatedEntity, existingEntity, getNullPropertyNames(updatedEntity));
            daoEvaluationReports.save(existingEntity);
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
