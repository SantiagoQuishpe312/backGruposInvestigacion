package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDocument;
import ec.edu.espe.GrupoInvestigacion.service.IServiceDocument;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/document")
@CrossOrigin(origins = "*")
@Tag(name = "DocumentController", description = "Controlador para gestionar el tipo de documentos")
public class DocumentController {
    @Autowired
    private IServiceDocument serviceDocument;

    @Operation(summary = "Obtener todos los documentos")
    @GetMapping("/")
    public ResponseEntity<List<DtoDocument>> getAllDocuments() {
        return new ResponseEntity<>(serviceDocument.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un documento por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<DtoDocument> getDocumentById(@PathVariable Long id) {
        return new ResponseEntity<>(serviceDocument.find(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo documento")
    @PostMapping("/create")
    public ResponseEntity<Long> createDocument(@RequestBody DtoDocument dtoDocument) {
        Long savedEvent = serviceDocument.save(dtoDocument);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un evento existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateDocument(@PathVariable Long id,@RequestBody DtoDocument dtoDocument){
        DtoDocument existingEvent=serviceDocument.find(id);
        if(existingEvent==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dtoDocument.setIdDocument(id);
        serviceDocument.update(dtoDocument);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
