package ec.edu.espe.GrupoInvestigacion.service;


import ec.edu.espe.GrupoInvestigacion.dao.DaoAssetsReport;
import ec.edu.espe.GrupoInvestigacion.dao.DaoAssets_Details;
import ec.edu.espe.GrupoInvestigacion.dto.DtoAssetsReport;
import ec.edu.espe.GrupoInvestigacion.dto.DtoAssetsReportGetData;
import ec.edu.espe.GrupoInvestigacion.mapper.AssetsDetailsMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.AssetsReportMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelAssetsReport;
import ec.edu.espe.GrupoInvestigacion.model.ModelAssets_Details;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceAssetsReport implements IServiceAssetsReport{
    @Autowired
    private DaoAssetsReport daoAssetsReport;
    @Autowired
    private AssetsReportMapper assetsReportMapper;

    @Autowired
    private DaoAssets_Details daoAssetsDetails;

    @Autowired
    private AssetsDetailsMapper assetsDetailsMapper;


    @Override
    public List<DtoAssetsReport> findAll(){
        return daoAssetsReport.findAllEnable().orElse(new ArrayList<>()).stream().map(assetsReportMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public DtoAssetsReport find(Long id){
        return assetsReportMapper.toDTO(daoAssetsReport.findByIdEnable(id).orElse(new ModelAssetsReport()));
    }

    @Override
    public DtoAssetsReportGetData findAllData(Long id){
        Optional<ModelAssetsReport> modelAssetsReport=daoAssetsReport.findByGroup(id);
        if(modelAssetsReport.isEmpty()){
            throw new NoSuchElementException("");
        }
        Long idReport=modelAssetsReport.get().getId();

        Optional<List<ModelAssets_Details>> modelAssetsDetails=daoAssetsDetails.findByReport(idReport);
        DtoAssetsReportGetData dto=new DtoAssetsReportGetData();
        dto.setReporteBienes(modelAssetsReport.map(assetsReportMapper::toDTO).orElse(new DtoAssetsReport()));

        dto.setDetalleBienes(modelAssetsDetails.orElse(new ArrayList<>()).stream().map(assetsDetailsMapper::toDTO).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public Long save(DtoAssetsReport dtoAssetsReport) {
        ModelAssetsReport modelAssetsReport = assetsReportMapper.toEntity(dtoAssetsReport);
        ModelAssetsReport createdEntity = daoAssetsReport.save(modelAssetsReport);
        return createdEntity.getId();
    }

    @Override
    public void update(DtoAssetsReport dtoAssetsReport) {
        ModelAssetsReport existingEntity = daoAssetsReport.findByIdEnable(dtoAssetsReport.getIdReporteActivos()).orElse(null);
        if(existingEntity != null){
            ModelAssetsReport updateEntity = assetsReportMapper.toEntity(dtoAssetsReport);
            BeanUtils.copyProperties(updateEntity, existingEntity, getNullPropertyNames(updateEntity));
            daoAssetsReport.save(existingEntity);
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
