package ec.edu.espe.GrupoInvestigacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
@Data
@Entity
@Table(name = "UZITGCHECKLIST", schema = "UTIC")
public class ModelChecklist {
    @Id
    @GeneratedValue(generator = "UZITGCHECKLIST_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGCHECKLIST_Sequence", sequenceName = "UZISGCHECKLIST")
    @Column(name = "UZITGCHECKLIST_ID")
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.DATE)
    @Column(name = "UZITGCHECKLIST_DATE", nullable = false)
    private Date checklistDate = java.sql.Date.valueOf(LocalDate.now());  // Asigna la fecha actual

    @Column(name = "UZITGCHECKLIST_REQUEST_MEMO", nullable = false)
    private Boolean requestMemo;

    @Column(name = "UZITGCHECKLIST_INV_GR_FORM", nullable = false)
    private Boolean invGrForm;

    @Column(name = "UZITGCHECKLIST_DEV_GR_PLAN", nullable = false)
    private Boolean devGrPlan;

    @Column(name = "UZITGCHECKLIST_ECONO_PLAN", nullable = false)
    private Boolean econoPlan;

    @Column(name = "UZITGCHECKLIST_C_VITAE", nullable = false)
    private Boolean CVitae;

    @Column(name = "UZITGCHECKLIST_CERTIFICATE", nullable = false)
    private Boolean certificate;

    @Column(name = "UZITGCHECKLIST_RECEIVEBY", nullable = false)
    private String receiveBy;

    @Column(name = "UZITGCHECKLIST_DELIVEREDBY", nullable = false)
    private String deliveredBy;

    @Column(name = "UZITGCHECKLIST_USER_CREATE")
    private String userCreate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGCHECKLIST_DATE_CREATE")
    private Date dateCreate;

    @Column(name = "UZITGCHECKLIST_USER_MODIFICATE")
    private String userModificate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGCHECKLIST_DATE_MODIFICATE")
    private Date dateModificate;
    @ManyToOne
    @JoinColumn(name = "UZITGINV_GROUP_ID", nullable = false)
    private ModelInvGroup modelInvGroup;
}
