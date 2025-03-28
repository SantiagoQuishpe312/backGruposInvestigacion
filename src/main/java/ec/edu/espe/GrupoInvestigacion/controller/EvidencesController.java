package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoEvidences;
import ec.edu.espe.GrupoInvestigacion.service.IServiceEvidences;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/evidences")
@CrossOrigin(origins = "*")
@Tag(name = "EvidencesController", description = "Controlador para gestionar las evidencias")
public class EvidencesController {

    @Autowired
    private IServiceEvidences serviceEvidences;

    @Operation(summary = "Obtener todas las evidencias")
    @GetMapping("/")
    public ResponseEntity<List<DtoEvidences>> getAllEvidences() {
        return new ResponseEntity<>(serviceEvidences.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una evidencia por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<DtoEvidences> getEvidenceById(@PathVariable Long id) {
        return new ResponseEntity<>(serviceEvidences.find(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear una nueva evidencia")
    @PostMapping("/create")
    public ResponseEntity<Long> createEvidence(@RequestBody DtoEvidences dtoEvidences) {
        Long savedEvidence = serviceEvidences.save(dtoEvidences);
        return new ResponseEntity<>(savedEvidence, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una evidencia existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateEvidence(@PathVariable Long id, @RequestBody DtoEvidences dtoEvidences) {
        DtoEvidences existingEvidence = serviceEvidences.find(id);
        if (existingEvidence == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dtoEvidences.setId(id);
        serviceEvidences.update(dtoEvidences);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
