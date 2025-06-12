package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoObjGetStrategies;
import ec.edu.espe.GrupoInvestigacion.dto.DtoObjGetStrategiesRelation;
import ec.edu.espe.GrupoInvestigacion.dto.DtoObj_Strategies_ODS;
import ec.edu.espe.GrupoInvestigacion.service.IServiceObj_Strategies_ODS;
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
@RequestMapping(V1_API_VERSION + PRIVATE_PATH + "/obj_strategies_ods")
@CrossOrigin(origins = "*")
@Tag(name = "LineCreaController resource", description = "Controlador para gestionar peticiones de los objetivos especificos, estrategias institucionales y las ods relacionadas")
public class Obj_Strategies_ODSController {
    @Autowired
    private IServiceObj_Strategies_ODS serviceObjStrategiesOds;

    @Operation(summary = "Obtener datos por el Id del Objetivo Específico")
    @GetMapping("/objective/{id}")
    public ResponseEntity<List<DtoObj_Strategies_ODS>> getByObj(@PathVariable Long id){
        return new ResponseEntity<>(serviceObjStrategiesOds.findByObj(id), HttpStatus.OK);
    }
    @Operation(summary = "Obtener datos por el Id del Objetivo Específico")
    @GetMapping("/plan_relations/{id}")
    public ResponseEntity<List<DtoObj_Strategies_ODS>> getByPlanRelations(@PathVariable Long id){
        return new ResponseEntity<>(serviceObjStrategiesOds.findByPlanRelations(id), HttpStatus.OK);
    }

    @Operation(summary = "Obtener datos por el Id del Plan de Desarrollo")
    @GetMapping("/developmentPlan/{id}")
    public ResponseEntity<List<DtoObjGetStrategies>> getCompleteByObj(@PathVariable Long id){
        return new ResponseEntity<>(serviceObjStrategiesOds.findCompleteByPlan(id), HttpStatus.OK);
    }
    @Operation(summary = "Obtener datos por el Id del Plan de Desarrollo")
    @GetMapping("/relation/developmentPlan/{id}")
    public ResponseEntity<List<DtoObjGetStrategiesRelation>> getCompleteByObjRel(@PathVariable Long id){
        return new ResponseEntity<>(serviceObjStrategiesOds.findCompleteByPlanRelations(id), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una lista completa con la informacion desde el id del objetivo")
    @GetMapping("/all-by-obj/{id}")
    public ResponseEntity<DtoObjGetStrategies> getAllByObj(@PathVariable Long id){
        return new ResponseEntity<>(serviceObjStrategiesOds.findCompleteByObj(id),HttpStatus.OK);
    }

    @Operation(summary = "Crear una nueva relacion de control")
    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody DtoObj_Strategies_ODS dtoObjStrategiesOds){
        Long saved=serviceObjStrategiesOds.save(dtoObjStrategiesOds);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }
    @Operation(summary = "Eliminar un marco legal con su relacion a un plan de desarrollo por su ID")
    @DeleteMapping("/{idObj}/strategy/{idStrategy}/ods/{idOds}")
    public ResponseEntity<Void> deleteObjStrategyOds(@PathVariable Long idObj, @PathVariable Long idStrategy, @PathVariable Long idOds) {
        serviceObjStrategiesOds.delete(idObj,idStrategy,idOds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
