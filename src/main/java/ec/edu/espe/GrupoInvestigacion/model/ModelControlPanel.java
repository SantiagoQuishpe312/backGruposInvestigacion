package ec.edu.espe.GrupoInvestigacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "UZITGCONTROLPANEL", schema = "UTIC")
public class ModelControlPanel {

    @Id
    @GeneratedValue(generator = "UZITGCONTROLPANEL_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGCONTROLPANEL_Sequence", sequenceName = "UZISGCONTROLPANEL")
    @Column(name = "UZITGCONTROLPANEL_ID")
    private Long id;
    @Column(name = "UZITGCONTROLPANEL_SPECIFIC_OBJ", nullable = false)
    private String specificObj;

    @Column(name = "UZITGCONTROLPANEL_ACTIVITY", nullable = false)
    private String activity;
    @Column(name = "UZITGCONTROLPANEL_RESPONSIBLE", nullable = false)
    private String responsible;
    @Column(name = "UZITGCONTROLPANEL_INDICATOR", nullable = false)
    private String indicator;

    @Column(name = "UZITGCONTROLPANEL_GOALS1")
    private String goals1;

    @Column(name = "UZITGCONTROLPANEL_GOALS2")
    private String goals2;
    @Column(name = "UZITGCONTROLPANEL_GOALS3")
    private String goals3;
    @Column(name = "UZITGCONTROLPANEL_GOALS4")
    private String goals4;
    @Column(name = "UZITGCONTROLPANEL_FINANCING")
    private Float financing;
    @Column(name = "UZITGCONTROLPANEL_OBSERVATION", nullable = false)
    private String observation;


    @Column(name = "UZITGCONTROLPANEL_USER_CREATE")
    private String userCreate;

    @Column(name = "UZITGCONTROLPANEL_DATE_CREATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "UZITGCONTROLPANEL_USER_MODIFIC")
    private String userModificate;

    @Column(name = "UZITGCONTROLPANEL_DATE_MODIFIC")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModificate;


    @ManyToOne
    @JoinColumn(name = "UZITGDEVELOPMENT_PLAN_ID", nullable = false)
    private ModelDevelopmentPlan modelDevelopmentPlan;


}
