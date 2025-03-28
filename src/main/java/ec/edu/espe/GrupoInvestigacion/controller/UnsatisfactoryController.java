package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoUnsatisfactory;
import ec.edu.espe.GrupoInvestigacion.service.IServiceUnsatisfactory;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/unsatisfactory")
@CrossOrigin(origins = "*")
@Tag(name = "UnsatisfactoryController", description = "Controlador para gestionar las insatisfacciones")
public class UnsatisfactoryController {

    @Autowired
    private IServiceUnsatisfactory serviceUnsatisfactory;

    @Operation(summary = "Obtener todas las insatisfacciones")
    @GetMapping("/")
    public ResponseEntity<List<DtoUnsatisfactory>> getAllUnsatisfactories() {
        return new ResponseEntity<>(serviceUnsatisfactory.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una insatisfacción por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<DtoUnsatisfactory> getUnsatisfactoryById(@PathVariable Long id) {
        return new ResponseEntity<>(serviceUnsatisfactory.find(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear una nueva insatisfacción")
    @PostMapping("/create")
    public ResponseEntity<Long> createUnsatisfactory(@RequestBody DtoUnsatisfactory dtoUnsatisfactory) {
        Long savedEvent = serviceUnsatisfactory.save(dtoUnsatisfactory);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una insatisfacción existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUnsatisfactory(@PathVariable Long id, @RequestBody DtoUnsatisfactory dtoUnsatisfactory) {
        DtoUnsatisfactory existingEvent = serviceUnsatisfactory.find(id);
        if (existingEvent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dtoUnsatisfactory.setId(id);
        serviceUnsatisfactory.update(dtoUnsatisfactory);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
