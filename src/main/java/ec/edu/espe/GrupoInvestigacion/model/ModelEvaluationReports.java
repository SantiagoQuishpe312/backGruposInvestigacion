package ec.edu.espe.GrupoInvestigacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "UZITGEVALUATION_REPORTS")
public class ModelEvaluationReports {
    @Id
    @GeneratedValue(generator = "UZITGEVALUATION_REPORTS_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGEVALUATION_REPORTS_Sequence", sequenceName = "UZISGEVALUATION_REPORTS")
    @Column(name = "UZITGEVALUATION_REPORTS_ID")
    private Long id;

    @Column(name="UZITGEVALUATION_REPORTS_STRATE")
    private Float strategicCompliance;

    @Column(name = "UZITGEVALUATION_REPORTS_OPERAT")
    private Float operativeCompliance;

    @Column(name = "UZITGEVALUATION_REPORTS_USER_C")
    private String userCreate;

    @Column(name = "UZITGEVALUATION_REPORTS_DATE_C")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "UZITGEVALUATION_REPORTS_USER_M")
    private String userModificate;

    @Column(name = "UZITGEVALUATION_REPORTS_DATE_M")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModificate;

    @ManyToOne
    @JoinColumn(name = "UZITGINV_GROUP_ID", nullable = false)
    private ModelInvGroup modelInvGroup;

    @OneToMany(mappedBy = "modelEvaluationReports")
    private List<ModelEvidences> modelEvidences;

}
