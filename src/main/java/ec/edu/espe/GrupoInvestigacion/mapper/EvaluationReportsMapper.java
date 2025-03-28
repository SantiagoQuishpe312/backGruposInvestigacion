package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoEvaluationsReport;
import ec.edu.espe.GrupoInvestigacion.model.ModelEvaluationReports;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EvaluationReportsMapper {

    @Mapping(source = "strategicCompliance", target = "cumplimientoEstrategico")
    @Mapping(source = "operativeCompliance", target = "cumplimientoOperativo")
    @Mapping(source = "userCreate", target = "usuarioCreacion")
    @Mapping(source = "dateCreate", target = "fechaCreacion")
    @Mapping(source = "userModificate", target = "usuarioModificacion")
    @Mapping(source = "dateModificate", target = "fechaModificacion")
    @Mapping(source = "modelInvGroup.id", target = "idGrupoInvestigacion")
    DtoEvaluationsReport toDto(ModelEvaluationReports modelEvaluationReports);

    @Mapping(source = "cumplimientoEstrategico", target = "strategicCompliance")
    @Mapping(source = "cumplimientoOperativo", target = "operativeCompliance")
    @Mapping(source = "usuarioCreacion", target = "userCreate")
    @Mapping(source = "fechaCreacion", target = "dateCreate")
    @Mapping(source = "usuarioModificacion", target = "userModificate")
    @Mapping(source = "fechaModificacion", target = "dateModificate")
    @Mapping(source = "idGrupoInvestigacion", target = "modelInvGroup.id")
    ModelEvaluationReports toEntity(DtoEvaluationsReport dtoEvaluationsReport);
}
