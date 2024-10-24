package ec.edu.espe.GrupoInvestigacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "UZITGDISLINK",schema = "UTIC")
public class ModelDislink {
    @Id
    @GeneratedValue(generator = "UZITGDISLINK_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGDISLINK_Sequence", sequenceName = "UZISGDISLINK")
    @Column(name = "UZITGDISLINK_ID")
    private Long id;

    @Column(name = "UZITGDISLINK_JUSTIFICATION")
    private String justification;

    @Column(name = "UZITGDISLINK_OBSERVATIONS")
    private String observations;


    @Column(name = "UZITGDISLINK_STATE")
    private Character state;

    @Column(name = "UZITGDISLINK_USER_CREATE")
    private String userCreate;

    @Column(name = "UZITGDISLINK_DATE_CREATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "UZITGDISLINK_USER_MODIFICATE")
    private String userModificate;

    @Column(name = "UZITGDISLINK_DATE_MODIFICATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModificate;
    @ManyToOne
    @JoinColumn(name = "UZITGINV_GROUP_ID")
    private ModelInvGroup modelInvGroup;
}
