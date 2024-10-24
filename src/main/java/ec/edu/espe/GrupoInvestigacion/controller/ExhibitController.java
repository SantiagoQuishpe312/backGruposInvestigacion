package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoExhibit;
import ec.edu.espe.GrupoInvestigacion.dto.DtoGroupRegForm;
import ec.edu.espe.GrupoInvestigacion.service.IServiceExhibit;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH  +"/exhibit")
@CrossOrigin(origins = "*")
@Tag(name = "ExhibitController", description = "Controlador para gestionar exhibit(anexos)")
public class ExhibitController {

    @Autowired
    private IServiceExhibit exhibitService;

    @Operation(summary = "Obtener todos los exhibit")
    @GetMapping("/")
    public ResponseEntity<List<DtoExhibit>> getAllExhibits() {
        return new ResponseEntity<>(exhibitService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un exhibit por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<DtoExhibit> getExhibitById(@PathVariable Long id) {
        return new ResponseEntity<>(exhibitService.find(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo exhibit")
    @PostMapping("/add")
    public ResponseEntity<Long> createExhibit(@RequestBody DtoExhibit dtoExhibit) {
        Long createExhibitId = exhibitService.create(dtoExhibit);
        return new ResponseEntity<>(createExhibitId, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar la info de un Anexo")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateExhibit(@PathVariable Long id,@RequestBody DtoExhibit dtoExhibit){
        DtoExhibit existingExhibit=exhibitService.find(id);
        if(existingExhibit==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dtoExhibit.setIdExhibit(id);
        exhibitService.update(dtoExhibit);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
