package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDislink;
import ec.edu.espe.GrupoInvestigacion.model.ModelDislink;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DislinkMapper {
    @Mapping(source = "idDesvinculacion", target = "id")
    @Mapping(source = "idGrupoInv", target = "modelInvGroup.id")
    @Mapping(source = "justificacion", target = "justification")
    @Mapping(source = "observaciones", target = "observations")
    @Mapping(source = "estado", target = "state")
    @Mapping(source = "usuarioCreacion", target = "userCreate")
    @Mapping(source = "fechaCreacion", target = "dateCreate")
    @Mapping(source = "usuarioModificacion", target = "userModificate")
    @Mapping(source = "fechaModificacion", target = "dateModificate")
    ModelDislink toEntity(DtoDislink dto);

    @Mapping(source = "id", target = "idDesvinculacion")
    @Mapping(source = "modelInvGroup.id", target = "idGrupoInv")
    @Mapping(source = "justification", target = "justificacion")
    @Mapping(source = "observations", target = "observaciones")
    @Mapping(source = "state", target = "estado")
    @Mapping(source = "userCreate", target = "usuarioCreacion")
    @Mapping(source = "dateCreate", target = "fechaCreacion")
    @Mapping(source = "userModificate", target = "usuarioModificacion")
    @Mapping(source = "dateModificate", target = "fechaModificacion")
    DtoDislink toDto(ModelDislink model);
}
