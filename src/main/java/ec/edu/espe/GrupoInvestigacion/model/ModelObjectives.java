package ec.edu.espe.GrupoInvestigacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = " UZITGOBJECTIVES", schema = "UTIC")
public class ModelObjectives {
    @Id
    @GeneratedValue(generator = "UZITGOBJECTIVES_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGOBJECTIVES_Sequence", sequenceName = "UZISGOBJECTIVES")
    @Column(name = "UZITGOBJECTIVES_ID")
    private Long id;

    @Column(name = "UZITGOBJECTIVES_OBJ", nullable = false)
    private String objective;


    @Column(name = "UZITGOBJECTIVES_USER_CREATE")
    private String userCreate;

    @Column(name = "UZITGOBJECTIVES_DATE_CREATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "UZITGOBJECTIVES_USER_MODIFICAT")
    private String userModificate;

    @Column(name = "UZITGOBJECTIVES_DATE_MODIFICAT")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModificate;

    @ManyToOne
    @JoinColumn(name = "UZITGDEVELOPMENT_PLAN_ID", nullable = false)
    private ModelDevelopmentPlan modelDevelopmentPlan;
    @OneToMany(mappedBy = "modelObjectives", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ModelStrategies> strategies;

}
