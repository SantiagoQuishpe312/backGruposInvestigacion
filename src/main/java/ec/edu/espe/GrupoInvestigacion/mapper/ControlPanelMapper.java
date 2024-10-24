package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoControlPanel;
import ec.edu.espe.GrupoInvestigacion.model.ModelControlPanel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface ControlPanelMapper {

    @Mapping(source = "id", target = "idPanelControl")
    @Mapping(source = "modelDevelopmentPlan.id",target = "idPlanDesarrollo")
    @Mapping(source = "specificObj", target = "objetivoEspecifico")
    @Mapping(source = "activity", target = "actividad")
    @Mapping(source = "responsible", target = "responsable")
    @Mapping(source = "indicator", target = "indicador")
    @Mapping(source = "goals1", target = "meta1")
    @Mapping(source = "goals2", target = "meta2")
    @Mapping(source = "goals3", target = "meta3")
    @Mapping(source = "goals4", target = "meta4")
    @Mapping(source = "financing", target = "financiamiento")
    @Mapping(source = "observation", target = "observacion")
    @Mapping(source = "userCreate", target = "usuarioCreacionPanelControl")
    @Mapping(source = "dateCreate", target = "fechaCreacionPanelControl")
    @Mapping(source = "userModificate", target = "usuarioModificacionPanelControl")
    @Mapping(source = "dateModificate", target = "fechaModificacionPanelControl")
    DtoControlPanel toDto(ModelControlPanel model);
    @Mapping(source = "idPanelControl", target = "id")
    @Mapping(source = "idPlanDesarrollo",target = "modelDevelopmentPlan.id")
    @Mapping(source = "objetivoEspecifico", target = "specificObj")
    @Mapping(source = "actividad", target = "activity")
    @Mapping(source = "responsable", target = "responsible")
    @Mapping(source = "indicador", target = "indicator")
    @Mapping(source = "meta1", target = "goals1")
    @Mapping(source = "meta2", target = "goals2")
    @Mapping(source = "meta3", target = "goals3")
    @Mapping(source = "meta4", target = "goals4")
    @Mapping(source = "financiamiento", target = "financing")
    @Mapping(source = "observacion", target = "observation")
    @Mapping(source = "usuarioCreacionPanelControl", target = "userCreate")
    @Mapping(source = "fechaCreacionPanelControl", target = "dateCreate")
    @Mapping(source = "usuarioModificacionPanelControl", target = "userModificate")
    @Mapping(source = "fechaModificacionPanelControl", target = "dateModificate")
    ModelControlPanel toEntity(DtoControlPanel dtoControlPanel);
}
