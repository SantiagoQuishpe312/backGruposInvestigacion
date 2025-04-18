package ec.edu.espe.GrupoInvestigacion.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ModelAnnualControlId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "UZITGCONTROLPANEL_ID", insertable = false,updatable = false)
    private ModelControlPanel modelControlPanel;

    @ManyToOne
    @JoinColumn(name = "UZITGANNUAL_OP_PLAN_ID",insertable = false,updatable = false)
    private  ModelAnnualOperativePlan modelAnnualOperativePlan;

    @ManyToOne
    @JoinColumn(name = "UZITGODS_ID",insertable = false,updatable = false)
    private  ModelOds modelOds;

    @ManyToOne
    @JoinColumn(name = "UZITGSTRATEGIES_ID", insertable = false, updatable = false)
    private ModelStrategies modelStrategies;

}
