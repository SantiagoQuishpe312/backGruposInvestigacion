package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDislink;
import ec.edu.espe.GrupoInvestigacion.service.IServiceDislink;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/dislinks")
@CrossOrigin(origins = "*")
@Tag(name = "DislinkController", description = "Controlador para gestionar Dislinks")
public class DislinkController {

    @Autowired
    private IServiceDislink dislinkService;

    @Operation(summary = "Obtener todos los formularios de desvinculacion")
    @GetMapping("/")
    public ResponseEntity<List<DtoDislink>> getAllDislinks() {
        return new ResponseEntity<>(dislinkService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un formulario de desvinculacion por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<DtoDislink> getDislinkById(@PathVariable Long id) {
        return new ResponseEntity<>(dislinkService.find(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo formulario de desvinculacion")
    @PostMapping("/create")
    public ResponseEntity<Long> createDislink(@RequestBody DtoDislink dtoDislink) {
        Long savedDislink = dislinkService.save(dtoDislink);
        return new ResponseEntity<>(savedDislink, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un formulario de desvinculacion existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateDislink(@PathVariable Long id,@RequestBody DtoDislink dtoDislink) {
        DtoDislink existingDislink = dislinkService.find(id);
        if (existingDislink == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dtoDislink.setIdDesvinculacion(id);
        dislinkService.update(dtoDislink);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
