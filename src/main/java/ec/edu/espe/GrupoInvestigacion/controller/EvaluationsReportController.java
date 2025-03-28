package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoEvaluationsReport;
import ec.edu.espe.GrupoInvestigacion.service.IServiceEvaluationReports;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/evaluationsreport")
@CrossOrigin(origins = "*")
@Tag(name = "EvaluationsReportController", description = "Controlador para gestionar los informes de evaluación")
public class EvaluationsReportController {

    @Autowired
    private IServiceEvaluationReports serviceEvaluationsReport;

    @Operation(summary = "Obtener todos los informes de evaluación")
    @GetMapping("/")
    public ResponseEntity<List<DtoEvaluationsReport>> getAllEvaluationsReports() {
        return new ResponseEntity<>(serviceEvaluationsReport.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un informe de evaluación por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<DtoEvaluationsReport> getEvaluationsReportById(@PathVariable Long id) {
        return new ResponseEntity<>(serviceEvaluationsReport.find(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo informe de evaluación")
    @PostMapping("/create")
    public ResponseEntity<Long> createEvaluationsReport(@RequestBody DtoEvaluationsReport dtoEvaluationsReport) {
        Long savedEvaluationsReport = serviceEvaluationsReport.save(dtoEvaluationsReport);
        return new ResponseEntity<>(savedEvaluationsReport, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un informe de evaluación existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateEvaluationsReport(@PathVariable Long id, @RequestBody DtoEvaluationsReport dtoEvaluationsReport) {
        DtoEvaluationsReport existingEvaluationsReport = serviceEvaluationsReport.find(id);
        if (existingEvaluationsReport == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dtoEvaluationsReport.setId(id);
        serviceEvaluationsReport.update(dtoEvaluationsReport);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
