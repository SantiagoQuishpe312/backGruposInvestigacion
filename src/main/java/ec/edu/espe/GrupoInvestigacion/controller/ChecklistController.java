package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoChecklist;
import ec.edu.espe.GrupoInvestigacion.dto.DtoGroupRegForm;
import ec.edu.espe.GrupoInvestigacion.service.IServiceChecklist;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH  +"/checklist")
@CrossOrigin(origins = "*")
@Tag(name = "ChecklistController", description = "Controlador para gestionar listas de verificación (checklist)")
public class ChecklistController {

    @Autowired
    private IServiceChecklist checklistService;

    @Operation(summary = "Obtener todos los checklist")
    @GetMapping("/")
    public ResponseEntity<List<DtoChecklist>> getAllChecklists() {
        return new ResponseEntity<>(checklistService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un checklist por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<DtoChecklist> getChecklistById(@PathVariable Long id) {
        return new ResponseEntity<>(checklistService.find(id), HttpStatus.OK);
    }
    @Operation(summary = "Obtener un checklist por el ID del grupo")
    @GetMapping("/byGroup/{id}")
    public ResponseEntity<DtoChecklist> getChecklistByGroup(@PathVariable Long id) {
        return new ResponseEntity<>(checklistService.findByGroup(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo checklist")
    @PostMapping("/create")
    public ResponseEntity<Long> createChecklist(@RequestBody DtoChecklist dtoChecklist) {
        System.out.println("DtoChecklist received: " + dtoChecklist);
        Long savedChecklist=checklistService.save(dtoChecklist);

        return new ResponseEntity<>(savedChecklist, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un checklist existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateChecklist(@PathVariable Long id, @RequestBody DtoChecklist dtoChecklist){
        DtoChecklist existingChecklist=checklistService.find(id);
        if(existingChecklist==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dtoChecklist.setIdChecklist(id);
        checklistService.update(dtoChecklist);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
