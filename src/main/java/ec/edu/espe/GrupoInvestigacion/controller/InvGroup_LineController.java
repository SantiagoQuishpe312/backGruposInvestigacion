package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup_Line;
import ec.edu.espe.GrupoInvestigacion.service.IServiceInvGroup_Line;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/line-creas")
@CrossOrigin(origins = "*")
@Tag(name = "LineCreaController resource", description = "Controlador para gestionar peticiones de creación de líneas de investigación")
public class InvGroup_LineController {

    @Autowired
    private IServiceInvGroup_Line lineCreaService;

    @Operation(summary = "Obtener todas las peticiones de creación de líneas")
    @GetMapping("/")
    public ResponseEntity<List<DtoInvGroup_Line>> getAlllinesGroup() {
        return new ResponseEntity<>(lineCreaService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener las peticiones de creación de línea por su id")
    @GetMapping("/{id}")
    public ResponseEntity<List<DtoInvGroup_Line>> getLineCreaById(@PathVariable Long id) {
        return new ResponseEntity<>(lineCreaService.find(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear una nueva peticion de creación de línea por su id")
    @PostMapping("/create")
    public ResponseEntity<Long> createLineCrea(@RequestBody DtoInvGroup_Line dtoInvGroupLine){
        Long saved=lineCreaService.save(dtoInvGroupLine);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }
    /*@Operation(summary = "Obtiene las líneas por el id del Formulario de Creacion")
    @GetMapping ("/byreq/{id}")
    public ResponseEntity <DtoReqGetLine> reqGetLine(@PathVariable Long id){
        return new ResponseEntity<>(lineCreaService.findByReq(id),HttpStatus.OK);
    }*/
}
