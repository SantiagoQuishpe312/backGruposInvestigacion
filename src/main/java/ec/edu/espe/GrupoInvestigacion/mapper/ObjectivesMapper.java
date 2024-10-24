package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoObjectives;
import ec.edu.espe.GrupoInvestigacion.model.ModelObjectives;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ObjectivesMapper {

    @Mapping(source = "id", target = "idObjetivo")
    @Mapping(source = "modelDevelopmentPlan.id", target = "idPlanDesarrollo")
    @Mapping(source = "objective", target = "objetivo")
    @Mapping(source = "userCreate", target = "usuarioCreacion")
    @Mapping(source = "dateCreate", target = "fechaCreacion")
    @Mapping(source = "userModificate", target = "usuarioModificacion")
    @Mapping(source = "dateModificate", target = "fechaModificacion")
    DtoObjectives toDto(ModelObjectives model);

    @Mapping(source = "idObjetivo", target = "id")
    @Mapping(source = "idPlanDesarrollo", target = "modelDevelopmentPlan.id")
    @Mapping(source = "objetivo", target = "objective")
    @Mapping(source = "usuarioCreacion", target = "userCreate")
    @Mapping(source = "fechaCreacion", target = "dateCreate")
    @Mapping(source = "usuarioModificacion", target = "userModificate")
    @Mapping(source = "fechaModificacion", target = "dateModificate")
    ModelObjectives toEntity(DtoObjectives dto);
}
