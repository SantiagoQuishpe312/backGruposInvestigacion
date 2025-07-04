package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.*;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDevGetUpperLevelPlan;
import ec.edu.espe.GrupoInvestigacion.dto.DtoDeveUppe;
import ec.edu.espe.GrupoInvestigacion.dto.DtoReqGetAcademicDom;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUpperLevelPlan;
import ec.edu.espe.GrupoInvestigacion.mapper.DeveUppeMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.UpperLevelPlanMapper;
import ec.edu.espe.GrupoInvestigacion.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ServiceDeveUppe implements IServiceDeveUppe{
    @Autowired
    private DaoDeveUppe daoDeveUppe;
    @Autowired
    private DeveUppeMapper deveUppeMapper;
    @Autowired
    private DaoDevelopmentPlan daoDevelopmentPlan;
    @Autowired
    private DaoUpperLevelPlan daoUpperLevelPlan;
@Autowired
private UpperLevelPlanMapper upperLevelPlanMapper;
    @Autowired
    public ServiceDeveUppe(DaoDeveUppe daoDeveUppe,DeveUppeMapper deveUppeMapper){
        this.daoDeveUppe=daoDeveUppe;
        this.deveUppeMapper=deveUppeMapper;
    }
    @Override
    public List<DtoDeveUppe> findAll(){
        return daoDeveUppe.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(deveUppeMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<DtoDeveUppe> find(Long id){
        return daoDeveUppe.findByIdEnable(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(deveUppeMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public Long save(DtoDeveUppe dtoDeveUppe){
        Long developmentPlanId= dtoDeveUppe.getIdPlan();
        Long upperPlanId= dtoDeveUppe.getIdPlanNivelSuperior();
        ModelDevelopmentPlan modelDevelopmentPlan=daoDevelopmentPlan.findByIdEnable(developmentPlanId).orElse(new ModelDevelopmentPlan());
        ModelUpperLevelPlan modelUpperLevelPlan=daoUpperLevelPlan.findByIdEnable(upperPlanId).orElse(new ModelUpperLevelPlan());
        ModelDeveUppe modelDeveUppe=deveUppeMapper.toEntity(dtoDeveUppe);
        ModelDeveUppeId id=new ModelDeveUppeId();
        id.setModelDevelopmentPlan(modelDevelopmentPlan);
        id.setModelUpperLevelPlan(modelUpperLevelPlan);
        modelDeveUppe.setId(id);
        daoDeveUppe.save(modelDeveUppe);

        return modelDeveUppe.getModelUpperLevelPlan().getId();
    }
    @Override
    public void delete(Long devId,Long id){
        Optional<ModelDeveUppe> deveLegaOptional=daoDeveUppe.findUpperLevelPlanSpecific(devId,id);
        if(deveLegaOptional.isPresent()){
            daoDeveUppe.delete(deveLegaOptional.get());

        }else{
            throw new RuntimeException("No se encontro datos");
        }
    }
    @Override
    public List<DtoUpperLevelPlan> findByDev(Long id){
        return daoDeveUppe.findUpperLevelPlan(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(upperLevelPlanMapper::toDto)
                .collect(Collectors.toList());
    }
}
