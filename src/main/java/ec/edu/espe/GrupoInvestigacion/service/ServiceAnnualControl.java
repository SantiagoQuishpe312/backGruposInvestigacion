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
        Long strategiesId=dtoAnnualControl.getIdEstrategia();
        Long odsId=dtoAnnualControl.getIdOds();
        ModelAnnualOperativePlan modelAnnualOperativePlan=daoAnnualOperativePlan.findByIdEnable(annualPlanId).orElse(new ModelAnnualOperativePlan());
        ModelControlPanel modelControlPanel=daoControlPanel.findByIdEnable(controlPanelId).orElse(new ModelControlPanel());
        ModelStrategies modelStrategies=daoStrategies.findByIdEnable(strategiesId).orElse(new ModelStrategies());
        ModelOds modelOds=daoOds.findByIdEnable(odsId).orElse(new ModelOds());
        ModelAnnualControlId id=new ModelAnnualControlId();
        ModelAnnualControl modelAnnualControl=annualControlMapper.toEntity(dtoAnnualControl);
        id.setModelControlPanel(modelControlPanel);
        id.setModelAnnualOperativePlan(modelAnnualOperativePlan);
        id.setModelStrategies(modelStrategies);
        id.setModelOds(modelOds);
        modelAnnualControl.setId(id);
        daoAnnualControl.save(modelAnnualControl);
        return modelAnnualControl.getModelAnnualOperativePlan().getId();
    }

    @Override
    public List<DtoAnnualControlGetDetails> findAllByPlan(Long id) {
        List<DtoAnnualControlGetDetails> dtoList = new ArrayList<>();

        // Obtener la lista de ModelAnnualControl por el id del plan
        Optional<List<ModelAnnualControl>> modelAnnualControls = daoAnnualControl.findByIdPlan(id);
        if (modelAnnualControls.isPresent()) {
            // Si se encuentran resultados, procesarlos
            List<ModelAnnualControl> lista = modelAnnualControls.get();
            for (ModelAnnualControl annualControl : lista) {

                // Buscar el ModelAnnualControl relacionado usando los ids necesarios
                Optional<ModelAnnualControl> modelAnnualControl1 = daoAnnualControl
                        .findByIds(annualControl.getModelAnnualOperativePlan().getId(),
                                annualControl.getModelControlPanel().getId(),
                                annualControl.getModelOds().getId(),
                                annualControl.getModelStrategies().getId());

                // Obtener las entidades relacionadas (si existen)
                Optional<ModelStrategies> modelStrategies = daoStrategies.findByIdEnable(annualControl.getModelStrategies().getId());
                Optional<ModelOds> modelOds = daoOds.findByIdEnable(annualControl.getModelOds().getId());

                // Crear y mapear el DTO de la entidad ModelAnnualControl
                DtoAnnualControlGetDetails dto = new DtoAnnualControlGetDetails();
                dto.setControlAnual(modelAnnualControl1.map(annualControlMapper::toDto).orElse(new DtoAnnualControl()));
                dto.setControlPanel(serviceControlPanel.findAllById(annualControl.getModelControlPanel().getId()));
                dto.setEstrategias(modelStrategies.map(strategiesMapper::toDto).orElse(new DtoStrategies()));
                dto.setOds(modelOds.map(odsMapper::toDto).orElse(new DtoOds()));

                // Agregar el DTO al listado
                dtoList.add(dto);
            }
        } else {
            // Si no se encuentran resultados
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
