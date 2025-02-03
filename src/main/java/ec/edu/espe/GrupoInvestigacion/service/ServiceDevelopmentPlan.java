package ec.edu.espe.GrupoInvestigacion.service;


import ec.edu.espe.GrupoInvestigacion.dao.*;
import ec.edu.espe.GrupoInvestigacion.dto.*;
import ec.edu.espe.GrupoInvestigacion.mapper.*;
import ec.edu.espe.GrupoInvestigacion.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceDevelopmentPlan implements IServiceDevelopmentPlan{
    @Autowired
    private DaoDevelopmentPlan daoDevelopmentPlan;

    @Autowired
    private DevelopmentPlanMapper developmentPlanMapper;

    @Autowired
    private UpperLevelPlanMapper upperLevelPlanMapper;

    @Autowired
    private LegalFrameworkMapper legalFrameworkMapper;

    @Autowired
    private NationalPlanMapper nationalPlanMapper;
    @Autowired
    private InstStrategicObjMapper instStrategicObjMapper;

    @Autowired
    private DaoDeveUppe daoUpperLevelPlan;
    @Autowired
    private DaoDeveNati daoDeveNati;
    @Autowired
    private DaoDeveLega daoDeveLega;
    @Autowired
    private DaoInstStrategicObj daoInstStrategicObj;
    @Autowired
    private ServiceControlPanel serviceControlPanel;
    @Autowired
    private ServiceObj_Strategies_ODS serviceObjStrategiesOds;
    @Override
    public DtoDevelopmentPlan find(Long id) {
        return developmentPlanMapper.toDto(daoDevelopmentPlan.findByIdEnable(id).orElse(new ModelDevelopmentPlan()));
    }
    public List<DtoDevelopmentPlan> findGroupC(Long id) {
        return daoDevelopmentPlan.findByGroup(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(developmentPlanMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<DtoDevelopmentPlan> findGroupType(Long id,Character tipo) {
        return daoDevelopmentPlan.findByGroupAndType(id,tipo)
                .orElse(new ArrayList<>())
                .stream()
                .map(developmentPlanMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoDevelopmentPlanGetData findAllByGroupTypeState(Long id, Character tipo, Character estado) {
        Optional<ModelDevelopmentPlan> modelDevelopmentPlan = daoDevelopmentPlan.findByGroupTypeState(id, tipo, estado);

        if (modelDevelopmentPlan.isEmpty()) {
            throw new NoSuchElementException("No Development Plan found for id: " + id + ", type: " + tipo + ", state: " + estado);
        }

        Long idDev = modelDevelopmentPlan.get().getId();

        Optional<List<ModelUpperLevelPlan>> modelUpperLevelPlan = daoUpperLevelPlan.findUpperLevelPlan(idDev);
        Optional<List<ModelLegalFramework>> modelLegalFramework = daoDeveLega.findLegalFramework(idDev);
        Optional<List<ModelNationalPlan>> modelNationalPlan = daoDeveNati.findNationalPlan(idDev);
        Optional<ModelInstStrategicObj> modelInstStrategicObj = daoInstStrategicObj.findByIdObj(
                modelDevelopmentPlan.get().getModelInstStrategicObj().getId()
        );

        DtoDevelopmentPlanGetData dto = new DtoDevelopmentPlanGetData();

        dto.setPlanDesarrollo(modelDevelopmentPlan.map(developmentPlanMapper::toDto).orElse(new DtoDevelopmentPlan()));
        dto.setPanelControl(serviceControlPanel.findCompleteByDev(idDev));
        dto.setPlanSuperior(modelUpperLevelPlan.orElse(new ArrayList<>()).stream().map(upperLevelPlanMapper::toDto).collect(Collectors.toList()));
        dto.setMarcoLegal(modelLegalFramework.orElse(new ArrayList<>()).stream().map(legalFrameworkMapper::toDto).collect(Collectors.toList()));
        dto.setPlanNacional(modelNationalPlan.orElse(new ArrayList<>()).stream().map(nationalPlanMapper::toDto).collect(Collectors.toList()));
        dto.setObjEstrategicoInst(modelInstStrategicObj.map(instStrategicObjMapper::toDTO).orElse(new DtoInstStrategicObj()));
        dto.setObjEstrategiasOds(serviceObjStrategiesOds.findCompleteByPlan(idDev));

        return dto;
    }


    @Override
    public List<DtoDevelopmentPlan> findAll() {
        return daoDevelopmentPlan.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(developmentPlanMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long create(DtoDevelopmentPlan dtoDevelopmentPlan) {
        ModelDevelopmentPlan modelDevelopmentPlan = developmentPlanMapper.toEntity(dtoDevelopmentPlan);
        ModelDevelopmentPlan createdEntity = daoDevelopmentPlan.save(modelDevelopmentPlan);
        return createdEntity.getId();
    }

    @Override
    public  void update(DtoDevelopmentPlan dtoDevelopmentPlan){
        ModelDevelopmentPlan existingEntity=daoDevelopmentPlan.findByIdEnable(dtoDevelopmentPlan.getIdPlanDesarrollo()).orElse(null);
        if(existingEntity!=null){
            ModelDevelopmentPlan updatedEntity=developmentPlanMapper.toEntity(dtoDevelopmentPlan);
            BeanUtils.copyProperties(updatedEntity,existingEntity,getNullPropertyNames(updatedEntity));
            daoDevelopmentPlan.save(existingEntity);
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
