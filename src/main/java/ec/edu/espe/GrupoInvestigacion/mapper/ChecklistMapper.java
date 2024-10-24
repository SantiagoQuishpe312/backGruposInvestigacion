package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoChecklist;
import ec.edu.espe.GrupoInvestigacion.model.ModelChecklist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChecklistMapper {

    @Mapping(source = "id", target = "idChecklist")
    @Mapping(source = "modelInvGroup.id", target = "idGrupoInv")

    @Mapping(source = "checklistDate", target = "fechaChecklist")
    @Mapping(source = "requestMemo", target = "peticionMemorando")
    @Mapping(source = "invGrForm", target = "formularioGrInv")
    @Mapping(source = "devGrPlan", target = "planDevGr")
    @Mapping(source = "econoPlan", target = "planEconomico")
    @Mapping(source = "CVitae" , target = "hojaVida")
    @Mapping(source = "certificate", target = "certificado")
    @Mapping(source = "receiveBy", target = "recibidoPor")
    @Mapping(source = "deliveredBy", target = "enviadoPor")
    @Mapping(source = "userCreate", target = "usuarioCreacionChecklist")
    @Mapping(source = "dateCreate", target = "fechaCreacionChecklist")
    @Mapping(source = "userModificate", target = "usuarioModificacionChecklist")
    @Mapping(source = "dateModificate", target = "fechaModificacionChecklist")
    DtoChecklist toDto(ModelChecklist model);
    @Mapping(source = "idChecklist", target = "id")
    @Mapping(source = "idGrupoInv", target = "modelInvGroup.id")
    @Mapping(source = "fechaChecklist", target = "checklistDate")
    @Mapping(source = "peticionMemorando", target = "requestMemo")
    @Mapping(source = "formularioGrInv", target = "invGrForm")
    @Mapping(source = "planDevGr", target = "devGrPlan")
    @Mapping(source = "planEconomico", target = "econoPlan")
    @Mapping(source = "hojaVida" , target = "CVitae")
    @Mapping(source = "certificado", target = "certificate")
    @Mapping(source = "recibidoPor", target = "receiveBy")
    @Mapping(source = "enviadoPor", target = "deliveredBy")
    @Mapping(source = "usuarioCreacionChecklist", target = "userCreate")
    @Mapping(source = "fechaCreacionChecklist", target = "dateCreate")
    @Mapping(source = "usuarioModificacionChecklist", target = "userModificate")
    @Mapping(source = "fechaModificacionChecklist", target = "dateModificate")
    ModelChecklist toEntity(DtoChecklist dtoChecklist);

}
