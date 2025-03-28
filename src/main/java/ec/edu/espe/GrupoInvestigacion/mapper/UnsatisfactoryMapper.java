package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoUnsatisfactory;
import ec.edu.espe.GrupoInvestigacion.model.ModelUnsatisfactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UnsatisfactoryMapper {

    @Mapping(source = "modelClosure.id", target = "idCierre")
    @Mapping(source = "record", target = "registro")
    @Mapping(source = "criteria", target = "criteriosNoSatisfactorio")
    @Mapping(source = "actions", target = "accionesNoSatisfactorio")
    @Mapping(source = "userCreate", target = "usuarioCreacion")
    @Mapping(source = "dateCreate", target = "fechaCreacion")
    @Mapping(source = "userModificate", target = "usuarioModificacion")
    @Mapping(source = "dateModificate", target = "fechaModificacion")
    DtoUnsatisfactory toDto(ModelUnsatisfactory modelUnsatisfactory);

    @Mapping(source = "idCierre", target = "modelClosure.id")
    @Mapping(source = "registro", target = "record")
    @Mapping(source = "criteriosNoSatisfactorio", target = "criteria")
    @Mapping(source = "accionesNoSatisfactorio", target = "actions")
    @Mapping(source = "usuarioCreacion", target = "userCreate")
    @Mapping(source = "fechaCreacion", target = "dateCreate")
    @Mapping(source = "usuarioModificacion", target = "userModificate")
    @Mapping(source = "fechaModificacion", target = "dateModificate")
    ModelUnsatisfactory toEntity(DtoUnsatisfactory dtoUnsatisfactory);
}
