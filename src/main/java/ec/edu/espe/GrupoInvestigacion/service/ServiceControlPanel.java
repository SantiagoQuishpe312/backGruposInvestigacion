package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoControlPanel;
import ec.edu.espe.GrupoInvestigacion.dao.DaoDevelopmentPlan;
import ec.edu.espe.GrupoInvestigacion.dao.DaoSpecificObjectives;
import ec.edu.espe.GrupoInvestigacion.dao.DaoUser;
import ec.edu.espe.GrupoInvestigacion.dto.DtoControlPanel;
import ec.edu.espe.GrupoInvestigacion.dto.DtoControlPanelGetData;
import ec.edu.espe.GrupoInvestigacion.dto.DtoSpecificObjectives;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUser;
import ec.edu.espe.GrupoInvestigacion.mapper.ControlPanelMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.SpecificObjectivesMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.UserMapper;
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
public class ServiceControlPanel implements IServiceControlPanel {

    @Autowired
    private DaoControlPanel daoControlPanel;
    @Autowired
    private DaoSpecificObjectives daoSpecificObjectives;

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoDevelopmentPlan daoDevelopmentPlan;
    @Autowired
    private ControlPanelMapper controlPanelMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SpecificObjectivesMapper specificObjectivesMapper;

    @Autowired
    public ServiceControlPanel(DaoControlPanel daoControlPanel,DaoDevelopmentPlan daoDevelopmentPlan, ControlPanelMapper controlPanelMapper){
        this.daoControlPanel=daoControlPanel;
        this.daoDevelopmentPlan=daoDevelopmentPlan;
        this.controlPanelMapper=controlPanelMapper;
    }

    @Override
    public DtoControlPanel find(Long id) {
        return controlPanelMapper.toDto(daoControlPanel.findByIdEnable(id).orElse(new ModelControlPanel()));
    }

    @Override
    public List<DtoControlPanel> findAll() {
        return daoControlPanel.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(controlPanelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoControlPanel> findByDev(Long id) {
        return daoControlPanel.findByIdDev(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(controlPanelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoControlPanelGetData> findCompleteByDev(Long id) {
        // Lista donde almacenaremos los DTOs a devolver
        List<DtoControlPanelGetData> dtoList = new ArrayList<>();

        // Obtener los datos del DAO
        Optional<List<ModelControlPanel>> modelControlPanel = daoControlPanel.findByIdDev(id);

        // Verificar si el Optional tiene valor
        if (modelControlPanel.isPresent()) {
            List<ModelControlPanel> lista = modelControlPanel.get();

            // Iterar sobre la lista
            for (ModelControlPanel controlPanel : lista) {
                // Obtener los datos relacionados
                Optional<ModelControlPanel> modelControlPanel1 = daoControlPanel.findByIdEnable(controlPanel.getId());
                Optional<ModelSpecificObjectives> modelSpecificObjectives = daoSpecificObjectives.findByIdEnable(controlPanel.getModelSpecificObjectives().getId());
                Optional<ModelUser> modelUser = daoUser.findByIdEnable(controlPanel.getModelUser().getIdUser());

                // Crear el DTO y asignar los valores
                DtoControlPanelGetData dto = new DtoControlPanelGetData();

                // Asignar los valores si existen
                dto.setPanelControl(modelControlPanel1.map(controlPanelMapper::toDto).orElse(new DtoControlPanel()));
                dto.setObjetivoEspecífico(modelSpecificObjectives.map(specificObjectivesMapper::toDto).orElse(new DtoSpecificObjectives()));
                dto.setResponsable(modelUser.map(userMapper::toDto).orElse(new DtoUser()));

                // Agregar el DTO a la lista
                dtoList.add(dto);
            }
        } else {
            System.out.println("No se encontraron resultados.");
        }

        // Retornar la lista de DTOs
        return dtoList;
    }

    @Override
    public Long save(DtoControlPanel dtoControlPanel){
        ModelControlPanel modelControlPanel=controlPanelMapper.toEntity(dtoControlPanel);
        ModelControlPanel createdEntity=daoControlPanel.save(modelControlPanel);
        return createdEntity.getId();
    }
    @Override
    public void update(DtoControlPanel dtoControlPanel){
        ModelControlPanel existingEntity=daoControlPanel.findByIdEnable(dtoControlPanel.getIdPanelControl()).orElse(null);
        if(existingEntity!=null){
            ModelControlPanel updatedEntity=controlPanelMapper.toEntity(dtoControlPanel);
            BeanUtils.copyProperties(updatedEntity,existingEntity,getNullPropertyNames(updatedEntity));
            daoControlPanel.save(existingEntity);
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
