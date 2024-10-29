package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoObj_Strategies_ODS;
import ec.edu.espe.GrupoInvestigacion.dao.DaoSpecificObjectives;
import ec.edu.espe.GrupoInvestigacion.dao.DaoOds;
import ec.edu.espe.GrupoInvestigacion.dao.DaoStrategies;
import ec.edu.espe.GrupoInvestigacion.dto.DtoObj_Strategies_ODS;
import ec.edu.espe.GrupoInvestigacion.mapper.Obj_Strategies_ODSMapper;
import ec.edu.espe.GrupoInvestigacion.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
