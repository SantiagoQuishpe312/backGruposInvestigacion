package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.reports.ReportService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.PRIVATE_PATH;
import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.V1_API_VERSION;
@RestController
@RequestMapping(V1_API_VERSION + PRIVATE_PATH  +"/creationRequest")

public class ReportController {
   private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generarReporte(){
        try{

            byte[] report =reportService.generarReport("SolicitudDeCreacion");
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition","inLine;filename=report.pdf");
            return new ResponseEntity<>(report,headers,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
}

}
