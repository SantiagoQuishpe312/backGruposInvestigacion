package ec.edu.espe.GrupoInvestigacion.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "UZITGEVIDENCES")
public class ModelEvidences {
    @Id
    @GeneratedValue(generator = "UZITGEVIDENCES", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGEVIDENCES_Sequence", sequenceName = "UZISGEVIDENCES")
    @Column(name = "UZITGEVIDENCES_ID")
    private Long id;

    @Column(name = "UZITGEVIDENCES_N")
    private String numero;

    @Column(name = "UZITGEVIDENCES_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "UZITGEVIDENCES_USER_CREATE")
    private String userCreate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGEVIDENCES_DATE_CREATE")
    private Date dateCreate;

    @Column(name = "UZITGEVIDENCES_USER_MODIFICATE")
    private String userModificate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGEVIDENCES_DATE_MODIFICATE")
    private Date dateModificate;

    @ManyToOne
    @JoinColumn(name = "UZITGANNEXES_ID", nullable = false)
    private ModelAnnexes modelAnnexes;

    @ManyToOne
    @JoinColumn(name = "UZITGEVALUATION_REPORTS_ID",nullable = false)
    private ModelEvaluationReports modelEvaluationReports;
}
