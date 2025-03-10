package ec.edu.espe.GrupoInvestigacion.reports;


import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroupGetData;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service

public class ReportService {
    public byte[] generatePdf(DtoInvGroupGetData data) {
        try {
            // Cargar el reporte JRXML
            InputStream reportStream = new ClassPathResource("reports/inv_group_report.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Convertir los datos en un mapa para JasperReports
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("nombreGrupoInv", data.getInvGroup().getNombreGrupoInv());
            parameters.put("estadoGrupoInv", data.getInvGroup().getEstadoGrupoInv());
            parameters.put("acronimoGrupoinv", data.getInvGroup().getAcronimoGrupoinv());
            parameters.put("mision", data.getInvGroup().getMision());
            parameters.put("vision", data.getInvGroup().getVision());
            parameters.put("departamento", data.getInvGroup().getDepartamento());
            parameters.put("proceso", data.getInvGroup().getProceso());
            parameters.put("fechaCreacionGI", data.getInvGroup().getFechaCreacionGI());

            // Agregar la lista de usuarios como DataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getUsers());

            // Generar el PDF
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF: " + e.getMessage(), e);
        }
    }
}
