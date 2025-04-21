package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.List;

@Data
public class DtoAssetsReportGetData {
    private DtoAssetsReport reporteBienes;
    private List<DtoAssets_Details> detalleBienes;

}
