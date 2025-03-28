package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoClosure;
import ec.edu.espe.GrupoInvestigacion.service.IServiceClosure;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/closure")
@CrossOrigin(origins = "*")
@Tag(name = "ClosureController", description = "Controlador para gestionar los cierres")
public class ClosureController {

    @Autowired
    private IServiceClosure serviceClosure;

    @Operation(summary = "Obtener todos los cierres")
    @GetMapping("/")
    public ResponseEntity<List<DtoClosure>> getAllClosures() {
        return new ResponseEntity<>(serviceClosure.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un cierre por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<DtoClosure> getClosureById(@PathVariable Long id) {
        return new ResponseEntity<>(serviceClosure.find(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo cierre")
    @PostMapping("/create")
    public ResponseEntity<Long> createClosure(@RequestBody DtoClosure dtoClosure) {
        Long savedClosure = serviceClosure.save(dtoClosure);
        return new ResponseEntity<>(savedClosure, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un cierre existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateClosure(@PathVariable Long id, @RequestBody DtoClosure dtoClosure) {
        DtoClosure existingClosure = serviceClosure.find(id);
        if (existingClosure == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dtoClosure.setId(id);
        serviceClosure.update(dtoClosure);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
