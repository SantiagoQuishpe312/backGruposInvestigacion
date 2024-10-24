package ec.edu.espe.GrupoInvestigacion.mapper;
import ec.edu.espe.GrupoInvestigacion.dto.DtoExhibit;
import ec.edu.espe.GrupoInvestigacion.model.ModelExhibit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")

public interface ExhibitMapper {
    @Mapping(source = "id", target = "idExhibit")
    @Mapping(source = "modelCreationReq.id",target = "idSolicitudCreacion")
    @Mapping(source = "name", target = "nombreExhibit")
    @Mapping(source = "route", target = "rutaExhibit")
    @Mapping(source = "userCreate", target = "usuarioCreacionExhibit")
    @Mapping(source = "dateCreate", target = "fechaCreacionExhibit")
    @Mapping(source = "userModificate", target = "usuarioModificacionExhibit")
    @Mapping(source = "dateModificate", target = "fechaModificacionExhibit")
    DtoExhibit toDto(ModelExhibit model);

    @Mapping(source = "idExhibit", target = "id")
    @Mapping(source = "idSolicitudCreacion",target = "modelCreationReq.id")
    @Mapping(source = "nombreExhibit", target = "name")
    @Mapping(source = "rutaExhibit", target = "route")
    @Mapping(source = "usuarioCreacionExhibit", target = "userCreate")
    @Mapping(source = "fechaCreacionExhibit", target = "dateCreate")
    @Mapping(source = "usuarioModificacionExhibit", target = "userModificate")
    @Mapping(source = "fechaModificacionExhibit", target = "dateModificate")
    ModelExhibit toEntity(DtoExhibit dto);

}
