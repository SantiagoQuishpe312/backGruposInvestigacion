package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoClosureRequest;
import ec.edu.espe.GrupoInvestigacion.model.ModelClosureRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClosureRequestMapper {

    @Mapping(source = "modelClosure.id", target = "idCierre")
    @Mapping(source = "Motivation", target = "motivacion")
    @Mapping(source = "Justification", target = "justificacion")
    @Mapping(source = "userCreate", target = "usuarioCreacion")
    @Mapping(source = "dateCreate", target = "fechaCreacion")
    @Mapping(source = "userModificate", target = "usuarioModificacion")
    @Mapping(source = "dateModificate", target = "fechaModificacion")
    DtoClosureRequest toDto(ModelClosureRequest modelClosureRequest);

    @Mapping(source = "idCierre", target = "modelClosure.id")
    @Mapping(source = "motivacion", target = "Motivation")
    @Mapping(source = "justificacion", target = "Justification")
    @Mapping(source = "usuarioCreacion", target = "userCreate")
    @Mapping(source = "fechaCreacion", target = "dateCreate")
    @Mapping(source = "usuarioModificacion", target = "userModificate")
    @Mapping(source = "fechaModificacion", target = "dateModificate")
    ModelClosureRequest toEntity(DtoClosureRequest dtoClosureRequest);
}
