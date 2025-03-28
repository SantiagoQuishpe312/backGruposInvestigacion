package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoClosureRequest;
import ec.edu.espe.GrupoInvestigacion.service.IServiceClosureRequest;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/closureRequest")
@CrossOrigin(origins = "*")
@Tag(name = "ClosureRequestController", description = "Controlador para gestionar las solicitudes de cierre")
public class ClosureRequestController {

    @Autowired
    private IServiceClosureRequest serviceClosureRequest;

    @Operation(summary = "Obtener todas las solicitudes de cierre")
    @GetMapping("/")
    public ResponseEntity<List<DtoClosureRequest>> getAllClosureRequests() {
        return new ResponseEntity<>(serviceClosureRequest.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una solicitud de cierre por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<DtoClosureRequest> getClosureRequestById(@PathVariable Long id) {
        return new ResponseEntity<>(serviceClosureRequest.find(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear una nueva solicitud de cierre")
    @PostMapping("/create")
    public ResponseEntity<Long> createClosureRequest(@RequestBody DtoClosureRequest dtoClosureRequest) {
        Long savedEvent = serviceClosureRequest.save(dtoClosureRequest);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una solicitud de cierre existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateClosureRequest(@PathVariable Long id, @RequestBody DtoClosureRequest dtoClosureRequest) {
        DtoClosureRequest existingEvent = serviceClosureRequest.find(id);
        if (existingEvent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dtoClosureRequest.setId(id);
        serviceClosureRequest.update(dtoClosureRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
