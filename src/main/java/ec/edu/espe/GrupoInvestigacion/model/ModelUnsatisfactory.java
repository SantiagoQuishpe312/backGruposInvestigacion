package ec.edu.espe.GrupoInvestigacion.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "UZITGUNSATISFACTORY", schema = "UTIC")
public class ModelUnsatisfactory {

    @Id
    @GeneratedValue(generator = "UZITGUNSATISFACTORY_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGUNSATISFACTORY_Sequence", sequenceName = "UZISGUNSATISFACTORY")
    @Column(name = "UZITGUNSATISFACTORY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UZITGCLOSURE_ID", nullable = false)
    private ModelClosure modelClosure;

    @Lob
    @Column(name = "UZITGUNSATISFACTORY_RECORD", columnDefinition = "CLOB")
    private String record;

    @Lob
    @Column(name = "UZITGUNSATISFACTORY_CRITERIA", columnDefinition = "CLOB")
    private String criteria;

    @Lob
    @Column(name = "UZITGUNSATISFACTORY_ACTIONS", columnDefinition = "CLOB")
    private String actions;

    @Column(name = "UZITGUNSATISFACTORY_USER_CREAT", length = 50)
    private String userCreate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGUNSATISFACTORY_DATE_CREAT")
    private Date dateCreate;

    @Column(name = "UZITGUNSATISFACTORY_USER_MODIF", length = 50)
    private String userModificate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGUNSATISFACTORY_DATE_MODIF")
    private Date dateModificate;
}
