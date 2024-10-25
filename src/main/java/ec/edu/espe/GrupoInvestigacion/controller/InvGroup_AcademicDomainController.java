package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup_AcademicDomain;
import ec.edu.espe.GrupoInvestigacion.service.IServiceInvGroup_AcademicDomain;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/acad-creas")
@CrossOrigin(origins = "*")
@Tag(name = "AcadCreaController", description = "Controlador para gestionar creación de domininio académico")

public class InvGroup_AcademicDomainController {

    @Autowired
    private IServiceInvGroup_AcademicDomain invGroupAcademicDomainService;

    @Operation(summary = "Obtener todas las creaciones de dominios académicos")
    @GetMapping("/")
    public ResponseEntity<List<DtoInvGroup_AcademicDomain>> getAllAcadCreas() {
        return new ResponseEntity<>(invGroupAcademicDomainService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una creacion de dominio académica por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<List<DtoInvGroup_AcademicDomain>> getAcadCreaById(@PathVariable Long id) {
        return new ResponseEntity<>(invGroupAcademicDomainService.find(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear una nueva creación de dominio académico")
    @PostMapping("/create")
    public ResponseEntity<Long> createAcadCrea(@RequestBody DtoInvGroup_AcademicDomain dtoInvGroupAcademicDomain) {
        Long saved=invGroupAcademicDomainService.save(dtoInvGroupAcademicDomain);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
   /* @Operation(summary = "Obtiene los dominios academicos por el id del Formulario de Creacion")
    @GetMapping ("/byreq/{id}")
    public ResponseEntity <DtoReqGetAcademicDom> reqGetAcademicDom(@PathVariable Long id){
        return new ResponseEntity<>(invGroupAcademicDomainService.findByReq(id),HttpStatus.OK);
    }*/

}
