package ec.edu.espe.GrupoInvestigacion.controller;

import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroupGetData;
import ec.edu.espe.GrupoInvestigacion.reports.ReportService;
import ec.edu.espe.GrupoInvestigacion.service.IServiceInvGroup;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import net.sf.jasperreports.engine.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.PRIVATE_PATH;
import static ec.edu.espe.GrupoInvestigacion.constant.GlobalConstant.V1_API_VERSION;
@RestController
@CrossOrigin(origins = "*")

@RequestMapping(V1_API_VERSION + PRIVATE_PATH  +"/creationRequest")

public class ReportController {
   private ReportService reportService;
    @Autowired
    private IServiceInvGroup invGroupService;
    @Autowired
    private ReportService pdfService;
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long id) {
        DtoInvGroupGetData data = invGroupService.findAllData(id);
        byte[] pdfBytes = pdfService.generatePdf(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=grupo_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}




