package ec.edu.espe.GrupoInvestigacion.mapper;

import ec.edu.espe.GrupoInvestigacion.dto.DtoUser;
import ec.edu.espe.GrupoInvestigacion.model.ModelUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "idUser", target = "id")
    @Mapping(source = "user", target = "usuario")
    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "institutionalId", target = "idInstitucional")
    @Mapping(source = "email", target = "correo")
    @Mapping(source = "department", target = "departamento")
    @Mapping(source = "identification", target = "cedula")
    @Mapping(source = "institution", target = "institucion")
    @Mapping(source = "position", target = "cargo")
    @Mapping(source = "nationality", target = "nacionalidad")
    @Mapping(source = "photo", target = "foto")
    @Mapping(source = "gender", target = "genero")
    @Mapping(source = "grade", target = "grado")
    @Mapping(source = "campus", target = "sede")
    @Mapping(source = "userCreate", target = "usuarioCreacion")
    @Mapping(source = "createdDate", target = "fechaCreacion")
    @Mapping(source = "userModificate", target = "usuarioModificacion")
    @Mapping(source = "modificatedDate", target = "fechaModificacion")
    DtoUser toDto(ModelUser model);

    @Mapping(source = "id", target = "idUser")
    @Mapping(source = "usuario", target = "user")
    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "idInstitucional", target = "institutionalId")
    @Mapping(source = "correo", target = "email")
    @Mapping(source = "departamento", target = "department")
    @Mapping(source = "cedula", target = "identification")
    @Mapping(source = "institucion", target = "institution")
    @Mapping(source = "cargo", target = "position")
    @Mapping(source = "nacionalidad", target = "nationality")
    @Mapping(source = "foto", target = "photo")
    @Mapping(source = "genero", target = "gender")
    @Mapping(source = "grado", target = "grade")
    @Mapping(source = "sede", target = "campus")
    @Mapping(source = "usuarioCreacion", target = "userCreate")
    @Mapping(source = "fechaCreacion", target = "createdDate")
    @Mapping(source = "usuarioModificacion", target = "userModificate")
    @Mapping(source = "fechaModificacion", target = "modificatedDate")
    ModelUser toEntity(DtoUser dtoUser);
}
