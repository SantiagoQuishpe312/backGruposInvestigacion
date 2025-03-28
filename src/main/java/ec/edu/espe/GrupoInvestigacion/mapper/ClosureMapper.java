package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoClosure;
import ec.edu.espe.GrupoInvestigacion.model.ModelClosure;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClosureMapper {

    @Mapping(source = "obj", target = "objCierre")
    @Mapping(source = "context", target = "contexto")
    @Mapping(source = "initialObj", target = "objInicial")
    @Mapping(source = "activities", target = "actividades")
    @Mapping(source = "acadImpact", target = "impactoAcad")
    @Mapping(source = "finImpact", target = "impactoFin")
    @Mapping(source = "Relocation", target = "reubicacion")
    @Mapping(source = "conclusion", target = "conclusion")
    @Mapping(source = "recomendation", target = "recomendacion")
    @Mapping(source = "process", target = "proceso")
    @Mapping(source = "userCreate", target = "usuarioCreacion")
    @Mapping(source = "dateCreate", target = "fechaCreacion")
    @Mapping(source = "userModificate", target = "usuarioModificacion")
    @Mapping(source = "dateModificate", target = "fechaModificacion")
    @Mapping(source = "modelInvGroup.id", target = "idGrupoInvestigacion")
    DtoClosure toDto(ModelClosure modelClosure);

    @Mapping(source = "objCierre", target = "obj")
    @Mapping(source = "contexto", target = "context")
    @Mapping(source = "objInicial", target = "initialObj")
    @Mapping(source = "actividades", target = "activities")
    @Mapping(source = "impactoAcad", target = "acadImpact")
    @Mapping(source = "impactoFin", target = "finImpact")
    @Mapping(source = "reubicacion", target = "Relocation")
    @Mapping(source = "conclusion", target = "conclusion")
    @Mapping(source = "recomendacion", target = "recomendation")
    @Mapping(source = "proceso", target = "process")
    @Mapping(source = "usuarioCreacion", target = "userCreate")
    @Mapping(source = "fechaCreacion", target = "dateCreate")
    @Mapping(source = "usuarioModificacion", target = "userModificate")
    @Mapping(source = "fechaModificacion", target = "dateModificate")
    @Mapping(source = "idGrupoInvestigacion", target = "modelInvGroup.id")
    ModelClosure toEntity(DtoClosure dtoClosure);
}
