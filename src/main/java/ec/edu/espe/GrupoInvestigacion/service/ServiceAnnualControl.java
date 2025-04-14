package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.*;
import ec.edu.espe.GrupoInvestigacion.dto.*;
import ec.edu.espe.GrupoInvestigacion.mapper.AnnualControlMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.ControlPanelMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.OdsMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.StrategiesMapper;
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
public class ServiceAnnualControl implements IServiceAnnualControl{
    @Autowired
    private DaoAnnualControl daoAnnualControl;
    @Autowired
    private AnnualControlMapper annualControlMapper;
    @Autowired
    private DaoControlPanel daoControlPanel;
    @Autowired
    private DaoAnnualOperativePlan daoAnnualOperativePlan;
    @Autowired
    private DaoStrategies daoStrategies;
    @Autowired
    private DaoOds daoOds;
    @Autowired
    private ControlPanelMapper controlPanelMapper;
    @Autowired
    private StrategiesMapper strategiesMapper;
    @Autowired
    private OdsMapper odsMapper;
    @Autowired
    private ServiceControlPanel serviceControlPanel;

    @Autowired
    public ServiceAnnualControl(DaoAnnualControl daoAnnualControl,AnnualControlMapper annualControlMapper){
        this.annualControlMapper=annualControlMapper;
        this.daoAnnualControl=daoAnnualControl;
    }


    @Override
    public List<DtoAnnualControl> findByPanel(Long id) {
        return daoAnnualControl.findByIdControl(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(annualControlMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoAnnualControl> findByPlan(Long id) {
        return daoAnnualControl.findByIdPlan(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(annualControlMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoAnnualControl findByIds(Long idPanel, Long idPlan) {
        return annualControlMapper.toDto(daoAnnualControl.findByIdControlPlan(idPanel, idPlan).orElse(new ModelAnnualControl()));
    }

    @Override
    public Long save(DtoAnnualControl dtoAnnualControl) {
        Long annualPlanId=dtoAnnualControl.getIdPlanAnual();
        Long controlPanelId=dtoAnnualControl.getIdPanelControl();
        ModelAnnualOperativePlan modelAnnualOperativePlan=daoAnnualOperativePlan.findByIdEnable(annualPlanId).orElse(new ModelAnnualOperativePlan());
        ModelControlPanel modelControlPanel=daoControlPanel.findByIdEnable(controlPanelId).orElse(new ModelControlPanel());
        ModelAnnualControl modelAnnualControl=annualControlMapper.toEntity(dtoAnnualControl);
        ModelAnnualControlId id=new ModelAnnualControlId();
        id.setModelControlPanel(modelControlPanel);
        id.setModelAnnualOperativePlan(modelAnnualOperativePlan);
        modelAnnualControl.setId(id);
        daoAnnualControl.save(modelAnnualControl);
        return modelAnnualControl.getModelAnnualOperativePlan().getId();
    }

    @Override
    public List<DtoAnnualControlGetDetails> findAllByPlan(Long id){
        List<DtoAnnualControlGetDetails> dtoList=new ArrayList<>();
        Optional<List<ModelAnnualControl>> modelAnnualControl=daoAnnualControl.findByIdPlan(id);
        if(modelAnnualControl.isPresent()){
            List<ModelAnnualControl> lista=modelAnnualControl.get();
            for(ModelAnnualControl annualControl:lista){
                Optional<ModelAnnualControl> modelAnnualControl1=daoAnnualControl.findByIds(annualControl.getModelAnnualOperativePlan().getId(),annualControl.getModelControlPanel().getId(),annualControl.getModelOds().getId(),annualControl.getModelStrategies().getId());
                //Optional<ModelControlPanel> modelControlPanel=daoControlPanel.findByIdEnable(annualControl.getModelControlPanel().getId());
                Optional<ModelStrategies> modelStrategies=daoStrategies.findByIdEnable(annualControl.getModelStrategies().getId());
                Optional<ModelOds> modelOds=daoOds.findByIdEnable(annualControl.getModelOds().getId());
                DtoAnnualControlGetDetails dto=new DtoAnnualControlGetDetails();
                dto.setControlAnual(modelAnnualControl1.map(annualControlMapper::toDto).orElse(new DtoAnnualControl()));
                dto.setControlPanel(serviceControlPanel.findAllById(annualControl.getModelControlPanel().getId()));
                dto.setEstrategias(modelStrategies.map(strategiesMapper::toDto).orElse(new DtoStrategies()));
                dto.setOds(modelOds.map(odsMapper::toDto).orElse(new DtoOds()));
                dtoList.add(dto);


            }
        }else{
            System.out.println("No se encontraron resultados.");

        }
        return dtoList;
    }
    @Override
    public void update(DtoAnnualControl dtoAnnualControl){
        ModelAnnualControl existingEntity=daoAnnualControl.findByIds(dtoAnnualControl.getIdPlanAnual(), dtoAnnualControl.getIdPanelControl(), dtoAnnualControl.getIdOds(), dtoAnnualControl.getIdEstrategia()).orElse(null);
        if(existingEntity!=null){
            ModelAnnualControl updatedEntity=annualControlMapper.toEntity(dtoAnnualControl);
            BeanUtils.copyProperties(updatedEntity,existingEntity,getNullPropertyNames(updatedEntity));
            daoAnnualControl.save(existingEntity);
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
