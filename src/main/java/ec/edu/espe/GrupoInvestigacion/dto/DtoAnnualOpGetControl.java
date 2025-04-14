package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.List;

@Data
public class DtoAnnualOpGetControl {
    private DtoAnnualOperativePlan planOpAnual;
    private List<DtoAnnualControlGetDetails> controlAnual;
}
