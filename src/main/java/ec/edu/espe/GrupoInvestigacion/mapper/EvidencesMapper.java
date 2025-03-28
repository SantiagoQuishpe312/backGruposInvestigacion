package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoEvidences;
import ec.edu.espe.GrupoInvestigacion.model.ModelEvidences;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EvidencesMapper {

    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "date", target = "fecha")
    @Mapping(source = "userCreate", target = "usuarioCreacion")
    @Mapping(source = "dateCreate", target = "fechaCreacion")
    @Mapping(source = "userModificate", target = "usuarioModificacion")
    @Mapping(source = "dateModificate", target = "fechaModificacion")
    @Mapping(source = "modelAnnexes.id", target = "idAnexo")
    @Mapping(source = "modelEvaluationReports.id", target = "idInformeEvaluacion")
    DtoEvidences toDto(ModelEvidences modelEvidences);

    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "fecha", target = "date")
    @Mapping(source = "usuarioCreacion", target = "userCreate")
    @Mapping(source = "fechaCreacion", target = "dateCreate")
    @Mapping(source = "usuarioModificacion", target = "userModificate")
    @Mapping(source = "fechaModificacion", target = "dateModificate")
    @Mapping(source = "idAnexo", target = "modelAnnexes.id")
    @Mapping(source = "idInformeEvaluacion", target = "modelEvaluationReports.id")
    ModelEvidences toEntity(DtoEvidences dtoEvidences);
}
