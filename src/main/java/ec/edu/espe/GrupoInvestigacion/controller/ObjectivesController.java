package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoObjectives;
import ec.edu.espe.GrupoInvestigacion.service.IServiceObjectives;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/objectives")
@CrossOrigin(origins = "*")
@Tag(name = "ObjectivesController", description = "Controlador para gestionar objetivos")
public class ObjectivesController {

    @Autowired
    private IServiceObjectives objectiveService;

    @Operation(summary = "Obtener todos los objetivos")
    @GetMapping("/")
    public ResponseEntity<List<DtoObjectives>> getAllObjectives() {
        return new ResponseEntity<>(objectiveService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener todos los objetivos por el id del plan de desarrollo")
    @GetMapping("/bydev/{id}")
    public ResponseEntity<List<DtoObjectives>> getObjectivesByDev(@PathVariable Long id) {
        return new ResponseEntity<>(objectiveService.findByDev(id), HttpStatus.OK);
    }
    @Operation(summary = "Obtener un objetivo por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<DtoObjectives> getObjectiveById(@PathVariable Long id) {
        return new ResponseEntity<>(objectiveService.find(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo objetivo")
    @PostMapping("/create")
    public ResponseEntity<Long> createObjective(@RequestBody DtoObjectives dtoObjective) {
        Long savedObjective = objectiveService.save(dtoObjective);
        return new ResponseEntity<>(savedObjective, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un objetivo existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateObjective(@PathVariable Long id,@RequestBody DtoObjectives dtoObjectives){
        DtoObjectives existingObjectives=objectiveService.find(id);
        if(existingObjectives==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dtoObjectives.setIdObjetivo(id);
        objectiveService.update(dtoObjectives);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
