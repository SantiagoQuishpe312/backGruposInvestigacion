package ec.edu.espe.GrupoInvestigacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = " UZITGSPECIFIC_OBJ", schema = "UTIC")
//actualizar el nombre a specific
public class ModelObjectives {
    @Id
    @GeneratedValue(generator = "UZITGSPECIFIC_OBJ_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGOBJECTIVES_Sequence", sequenceName = "UZISGOBJECTIVES")
    @Column(name = "UZITGSPECIFIC_OBJ_ID")
    private Long id;

    @Column(name = "UZITGSPECIFIC_OBJ_OBJ", nullable = false)
    private String objective;


    @Column(name = "UZITGSPECIFIC_OBJ_USER_CREATE")
    private String userCreate;

    @Column(name = "UZITGSPECIFIC_OBJ_DATE_CREATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "UZITGSPECIFIC_OBJ_USER_MODIFIC")
    private String userModificate;

    @Column(name = "UZITGSPECIFIC_OBJ_DATE_MODIFIC")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModificate;

    @ManyToOne
    @JoinColumn(name = "UZITGDEVELOPMENT_PLAN_ID", nullable = false)
    private ModelDevelopmentPlan modelDevelopmentPlan;
    @OneToMany(mappedBy = "modelObjectives", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ModelStrategies> strategies;

}
