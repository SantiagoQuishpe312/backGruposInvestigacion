package ec.edu.espe.GrupoInvestigacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "UZITGRELEVANCE_REPORT", schema = "UTIC")
public class ModelRelevanceReport {
    @Id
    @GeneratedValue(generator = "UZITGRELEVANCE_REPORT_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGRELEVANCE_REPORT_Sequence", sequenceName = "UZISGRELEVANCE_REPORT")
    @Column(name = "UZITGRELEVANCE_REPORT_ID")
    private Long id;


    @Column(name = "UZITGRELEVANCE_REPORT_N_MEMO")
    private String memoN;

    @Column(name = "UZITGRELEVANCE_REPORT_CREATION")
    private Boolean creationReq;

    @Column(name = "UZITGRELEVANCE_REPORT_DEVELOPM", nullable = false)
    private Boolean developmentPlan;

    @Column(name = "UZITGRELEVANCE_REPORT_ADITIONA", nullable = false)
    private Boolean aditionalDocs;

    @Column(name = "UZITGRELEVANCE_REPORT_OBJ")
    private String objectives;

    @Column(name = "UZITGRELEVANCE_REPORT_ODS_PLAN")
    private Boolean odsPlan;

    @Column(name = "UZITGRELEVANCE_REPORT_ACADEMIC")
    private String academicRelevance;

    @Column(name = "UZITGRELEVANCE_REPORT_AREA_LIN")
    private Boolean areaLine;

    @Column(name = "UZITGRELEVANCE_REPORT_COORDINA", nullable = false)
    private Boolean coordinator;

    @Column(name = "UZITGRELEVANCE_REPORT_MEMBERS", nullable = false)
    private Boolean members;

    @Column(name = "UZITGRELEVANCE_REPORT_OBJ_PLAN", nullable = false)
    private Boolean objectivesDevelopmentPlan;

    @Column(name = "UZITGRELEVANCE_REPORT_CONCLUSI")
    private String conclusions;
    @Column(name = "UZITGRELEVANCE_REPORT_RECOMEND")
    private String recomendations;


    @Column(name = "UZITGRELEVANCE_REPORT_USER_CRE")
    private String userCreate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGRELEVANCE_REPORT_DATE_CRE")
    private Date dateCreate;

    @Column(name = "UZITGRELEVANCE_REPORT_USER_MOD")
    private String userModificate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGRELEVANCE_REPORT_DATE_MOD")
    private Date dateModificate;

    @ManyToOne
    @JoinColumn(name = "UZITGINV_GROUP_ID", nullable = false)
    private ModelInvGroup modelInvGroup;


}
