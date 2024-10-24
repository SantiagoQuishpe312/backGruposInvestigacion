package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoStrategies;
import ec.edu.espe.GrupoInvestigacion.model.ModelStrategies;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StrategiesMapper {

    @Mapping(source = "id", target = "idEstrategia")
    @Mapping(source = "modelObjectives.id", target = "idObjetivo")
    @Mapping(source = "strategy", target = "estrategia")
    @Mapping(source = "sdg", target = "ods")
    @Mapping(source = "userCreate", target = "usuarioCreacion")
    @Mapping(source = "dateCreate", target = "fechaCreacion")
    @Mapping(source = "userModificate", target = "usuarioModificacion")
    @Mapping(source = "dateModificate", target = "fechaModificacion")
    DtoStrategies toDto(ModelStrategies model);

    @Mapping(source = "idEstrategia", target = "id")
    @Mapping(source = "idObjetivo", target = "modelObjectives.id")
    @Mapping(source = "estrategia", target = "strategy")
    @Mapping(source = "ods", target = "sdg")
    @Mapping(source = "usuarioCreacion", target = "userCreate")
    @Mapping(source = "fechaCreacion", target = "dateCreate")
    @Mapping(source = "usuarioModificacion", target = "userModificate")
    @Mapping(source = "fechaModificacion", target = "dateModificate")
    ModelStrategies toEntity(DtoStrategies dto);
}
