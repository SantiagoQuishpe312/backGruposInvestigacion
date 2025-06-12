package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoObj_Strategies_ODS;
import ec.edu.espe.GrupoInvestigacion.dao.DaoSpecificObjectives;
import ec.edu.espe.GrupoInvestigacion.dao.DaoOds;
import ec.edu.espe.GrupoInvestigacion.dao.DaoStrategies;
import ec.edu.espe.GrupoInvestigacion.dto.*;
import ec.edu.espe.GrupoInvestigacion.mapper.Obj_Strategies_ODSMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.OdsMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.SpecificObjectivesMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.StrategiesMapper;
import ec.edu.espe.GrupoInvestigacion.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceObj_Strategies_ODS implements IServiceObj_Strategies_ODS {
    @Autowired
    private DaoStrategies daoStrategies;
    @Autowired
   private DaoSpecificObjectives daoSpecificObjectives;
    @Autowired
    private DaoOds daoOds;
@Autowired
private SpecificObjectivesMapper specificObjectivesMapper;
@Autowired
private OdsMapper odsMapper;
@Autowired
private StrategiesMapper strategiesMapper;
    @Autowired
    private Obj_Strategies_ODSMapper objStrategiesOdsMapper;
    @Autowired
    private DaoObj_Strategies_ODS daoObjStrategiesOds;

    public ServiceObj_Strategies_ODS(Obj_Strategies_ODSMapper objStrategiesOdsMapper, DaoObj_Strategies_ODS daoObjStrategiesOds) {
        this.objStrategiesOdsMapper = objStrategiesOdsMapper;
        this.daoObjStrategiesOds = daoObjStrategiesOds;
    }

    @Override
    public List<DtoObj_Strategies_ODS> findByObj(Long id) {

        return daoObjStrategiesOds.findByObjEnable(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(objStrategiesOdsMapper::toDto)
                .collect(Collectors.toList());
    }@Override
    public List<DtoObj_Strategies_ODS> findByPlanRelations(Long id) {

        return daoObjStrategiesOds.findByPlanRelations(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(objStrategiesOdsMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public void delete (Long idObj, Long idStrategy, Long idOds){
        Optional<ModelObj_Strategies_ODS> objStrategiesOds=daoObjStrategiesOds.findEnable(idObj,idStrategy,idOds);
        if(objStrategiesOds.isPresent()){
            daoObjStrategiesOds.delete(objStrategiesOds.get());
        }else{
            throw new RuntimeException("No se encontro datos");

        }
    }
    @Override
    public List<DtoObjGetStrategiesRelation> findCompleteByPlanRelations(Long id) {
        System.out.println("🔍 Buscando objetivos específicos para el plan ID: " + id);

        Optional<List<ModelSpecificObjectives>> modelObj = daoObjStrategiesOds.findOdsObjByPlanEnable(id);
        List<DtoObjGetStrategiesRelation> list = new ArrayList<>();

        if (modelObj.isPresent() && !modelObj.get().isEmpty()) {
            for (ModelSpecificObjectives specificObjective : modelObj.get()) {
                System.out.println("➡️ Procesando ObjectiveSpecific ID: " + specificObjective.getId());

                DtoObjGetStrategiesRelation dto = new DtoObjGetStrategiesRelation();

                // Obtener DTO del objetivo
                DtoSpecificObjectives dtoObjective = daoSpecificObjectives.findByIdEnable(specificObjective.getId())
                        .map(specificObjectivesMapper::toDto)
                        .orElse(new DtoSpecificObjectives());

                System.out.println("✅ DTO del objetivo: " + dtoObjective.getObjetivo());
                dto.setObj(dtoObjective);

                // Obtener relaciones estrategia + ODS
                List<ModelObj_Strategies_ODS> relaciones = daoObjStrategiesOds.findBySpecificObjective2(specificObjective.getId())
                        .orElse(new ArrayList<>());

                System.out.println("🔗 Se encontraron " + relaciones.size() + " relaciones para el objetivo ID " + specificObjective.getId());

                List<DtoEstrategiaOds> estrategiasOds = relaciones.stream().map(rel -> {
                    DtoEstrategiaOds dtoRel = new DtoEstrategiaOds();

                    System.out.println("📌 Relación - Estrategia ID: " + rel.getModelStrategies().getId() + ", ODS ID: " + rel.getModelOds().getId());

                    dtoRel.setEstrategia(strategiesMapper.toDto(rel.getModelStrategies()));
                    dtoRel.setOds(odsMapper.toDto(rel.getModelOds()));
                    return dtoRel;
                }).collect(Collectors.toList());

                dto.setEstrategiasOds(estrategiasOds);
                list.add(dto);
            }
        } else {
            System.out.println("⚠️ No se encontraron objetivos específicos para el plan ID: " + id);
        }

        System.out.println("✅ Total DTOs construidos: " + list.size());
        return list;
    }


    @Override
    public List<DtoObjGetStrategies> findCompleteByPlan(Long id) {
        Optional<List<ModelSpecificObjectives>> modelObj = daoObjStrategiesOds.findOdsObjByPlanEnable(id);

        List<DtoObjGetStrategies> list = new ArrayList<>();

        if (modelObj.isPresent() && !modelObj.get().isEmpty()) {
            for (ModelSpecificObjectives specificObjective : modelObj.get()) {
                System.out.println("SpecificObjective ID: " + specificObjective.getId());
                DtoObjGetStrategies dtoObjGetStrategies = new DtoObjGetStrategies();

                // Asignar datos al DTO
                dtoObjGetStrategies.setObj(
                        daoSpecificObjectives.findByIdEnable(specificObjective.getId())
                                .map(specificObjectivesMapper::toDto)
                                .orElse(new DtoSpecificObjectives())
                );

                // Obtener estrategias habilitadas y asignarlas al DTO
                dtoObjGetStrategies.setStrategies(
                        daoStrategies.findBySpecificObj(specificObjective.getId())
                                .orElse(new ArrayList<>())
                                .stream()
                                .map(strategiesMapper::toDto) // Asume un mapper para convertir a DTO
                                .collect(Collectors.toList())
                );

                // Obtener y asignar los ODS (deberías completar cómo obtenerlos)
                dtoObjGetStrategies.setOds(
                        daoOds.findBySpecificObj(specificObjective.getId())
                                .orElse(new ArrayList<>())
                                .stream()
                                .map(odsMapper::toDto) // Asume un mapper para convertir a DTO
                                .collect(Collectors.toList())
                );

                // Agregar el DTO a la lista final
                list.add(dtoObjGetStrategies);
            }
        }

        // Devolver la lista de DTOs
        return list;
    }

@Override
public DtoObjGetStrategies findCompleteByObj(Long id){
        Optional<ModelSpecificObjectives> modelSpecificObjectives=daoSpecificObjectives.findByIdEnable(id);
        Optional<List<ModelOds>> modelOds=daoOds.findBySpecificObj(id);
        Optional<List<ModelStrategies>> modelStrategies=daoStrategies.findBySpecificObj(id);
        DtoObjGetStrategies dtoObjGetStrategies=new DtoObjGetStrategies();
        if(modelStrategies.isPresent()){
            dtoObjGetStrategies.setObj(modelSpecificObjectives.map(specificObjectivesMapper::toDto).orElse(new DtoSpecificObjectives()));
            dtoObjGetStrategies.setOds(modelOds.orElse(new ArrayList<>()).stream().map(odsMapper::toDto).collect(Collectors.toList()));
            dtoObjGetStrategies.setStrategies(modelStrategies.orElse(new ArrayList<>()).stream().map(strategiesMapper::toDto).collect(Collectors.toList()));
            return dtoObjGetStrategies;
        }
        throw new RuntimeException("No se encontro datos");
}

    @Override
    public Long save(DtoObj_Strategies_ODS dtoObjStrategiesOds) {

        Long objId=dtoObjStrategiesOds.getIdObjetivoEspecifico();
        Long strategiesId=dtoObjStrategiesOds.getIdEstrategia();
        Long odsId=dtoObjStrategiesOds.getIdODS();
        ModelSpecificObjectives modelSpecificObjectives= daoSpecificObjectives.findByIdEnable(objId).orElse(new ModelSpecificObjectives());
        ModelStrategies modelStrategies=daoStrategies.findByIdEnable(strategiesId).orElse(new ModelStrategies());
        ModelOds modelOds=daoOds.findByIdEnable(odsId).orElse(new ModelOds());
        ModelObj_Strategies_ODS modelObjStrategiesOds=objStrategiesOdsMapper.toEntity(dtoObjStrategiesOds);
        ModelObj_Strategies_ODS_Id id=new ModelObj_Strategies_ODS_Id();
        id.setModelSpecificObjectives(modelSpecificObjectives);
        id.setModelOds(modelOds);
        id.setModelStrategies(modelStrategies);
        modelObjStrategiesOds.setId(id);
        daoObjStrategiesOds.save(modelObjStrategiesOds);

        return modelObjStrategiesOds.getModelStrategies().getId();
    }
}
