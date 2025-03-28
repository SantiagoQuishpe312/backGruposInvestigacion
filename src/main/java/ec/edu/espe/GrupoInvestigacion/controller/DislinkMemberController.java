package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDislinkMember;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvMember;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUser;
import ec.edu.espe.GrupoInvestigacion.service.IServiceDislikMember;
import ec.edu.espe.GrupoInvestigacion.service.IServiceInvMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.PRIVATE_PATH;
import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.V1_API_VERSION;

@RestController
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/dislinks-members")
@CrossOrigin(origins = "*")
@Tag(name = "InvMemberController", description = "Controlador para gestionar miembros de investigación")
public class DislinkMemberController {
    @Autowired
    private IServiceDislikMember dislikMember;

    @Operation(summary = "Obtener todos los miembros de investigación")
    @GetMapping("/")
    public ResponseEntity<List<DtoDislinkMember>> getAllInvMembers() {
        return new ResponseEntity<>(dislikMember.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un miembro de investigación por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<List<DtoDislinkMember>> getInvMemberById(@PathVariable Long id) {
        return new ResponseEntity<>(dislikMember.find(id), HttpStatus.OK);
    }
    @Operation(summary = "Obtener los datos de los miembros del grupo de investigación por el id del grupo")
    @GetMapping("/groupId/{id}")
    public ResponseEntity<List<DtoUser>> getUsersData(@PathVariable Long id) {
        return new ResponseEntity<>(dislikMember.findInfoMembersByGroup(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo miembro de investigación")
    @PostMapping("/create")
    public ResponseEntity<Long> createInvMember(@RequestBody DtoDislinkMember dtoDislinkMember){
        Long savedInvMember=dislikMember.save(dtoDislinkMember);
        return new ResponseEntity<>(savedInvMember,HttpStatus.CREATED);
    }
    @Operation(summary="Obtiene miembros de investigacion por el id del grupo")
    @GetMapping("/group/{id}")
    public ResponseEntity<List<DtoDislinkMember>>getMembersByGroup(@PathVariable Long id){
        return new ResponseEntity<>(dislikMember.findGroup(id),HttpStatus.OK);
    }
    @Operation(summary = "Eliminar un usuario de un grupo por su ID")
    @DeleteMapping("/{userId}/group/{groupId}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        dislikMember.deleteUser(userId,groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Obtener información de un grupo de Investigación por su nombre de usuario")
    @GetMapping("/username/{username}")
    public ResponseEntity<DtoInvGroup> getInvMemberByUsername(@PathVariable String username) {
        return new ResponseEntity<>(dislikMember.findByUsername(username), HttpStatus.OK);
    }
    @Operation(summary = "Obtener un miembro de investigación por su username")
    @GetMapping("/byUsername/{username}")
    public ResponseEntity<List<DtoDislinkMember>> getInvMemberById(@PathVariable String username) {
        return new ResponseEntity<>(dislikMember.findByUserNameInvMember(username), HttpStatus.OK);
    }
}
