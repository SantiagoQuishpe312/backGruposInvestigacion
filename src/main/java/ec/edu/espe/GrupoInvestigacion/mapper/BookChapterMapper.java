package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoBookChapter;
import ec.edu.espe.GrupoInvestigacion.model.ModelBookChapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface BookChapterMapper {
    @Mapping(source = "id", target = "idLibroCapitulo")
    @Mapping(source = "modelActivityReport.id", target = "idInformeActividades")
    @Mapping(source = "number", target = "numero")
    @Mapping(source = "title", target = "titulo")
    @Mapping(source = "author", target = "autor")
    @Mapping(source = "book", target = "libro")
    @Mapping(source = "index", target = "indice")
    @Mapping(source = "userCreate", target = "usuarioCreacion")
    @Mapping(source = "dateCreate", target = "fechaCreacion")
    @Mapping(source = "userModificate", target = "usuarioModificacion")
    @Mapping(source = "dateModificate", target = "fechaModificacion")
    DtoBookChapter toDto(ModelBookChapter model);

    @Mapping(source = "idLibroCapitulo", target = "id")
    @Mapping(source = "idInformeActividades", target = "modelActivityReport.id")
    @Mapping(source = "numero", target = "number")
    @Mapping(source = "titulo", target = "title")
    @Mapping(source = "autor", target = "author")
    @Mapping(source = "libro", target = "book")
    @Mapping(source = "indice", target = "index")
    @Mapping(source = "usuarioCreacion", target = "userCreate")
    @Mapping(source = "fechaCreacion", target = "dateCreate")
    @Mapping(source = "usuarioModificacion", target = "userModificate")
    @Mapping(source = "fechaModificacion", target = "dateModificate")
    ModelBookChapter toEntity(DtoBookChapter dto);
}
