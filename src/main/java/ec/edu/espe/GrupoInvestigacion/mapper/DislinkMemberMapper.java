package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDislinkMember;
import ec.edu.espe.GrupoInvestigacion.model.ModelDislinkMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface DislinkMemberMapper {
    @Mapping(source = "modelInvGroup.id", target = "idGrupoInv")
    @Mapping(source = "modelUser.idUser", target = "idUsuario")
    @Mapping(source = "type", target = "tipo")
    @Mapping(source = "linkDate", target = "fechaVinculacion")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "userCreate", target = "usuarioCreacion")
    @Mapping(source = "dateCreate", target = "fechaCreacion")
    @Mapping(source = "userModificate", target = "usuarioModificacion")
    @Mapping(source = "dateModificate", target = "fechaModificacion")
    DtoDislinkMember toDto(ModelDislinkMember model);
    @Mapping(source = "idGrupoInv", target = "modelInvGroup.id")
    @Mapping(source = "idUsuario", target = "modelUser.idUser")
    @Mapping(source = "tipo", target = "type")
    @Mapping(source = "fechaVinculacion", target = "linkDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "usuarioCreacion", target = "userCreate")
    @Mapping(source = "fechaCreacion", target = "dateCreate")
    @Mapping(source = "usuarioModificacion", target = "userModificate")
    @Mapping(source = "fechaModificacion", target = "dateModificate")
    ModelDislinkMember toEntity(DtoDislinkMember dtoDislinkMember);
}
