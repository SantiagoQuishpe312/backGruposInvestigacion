package ec.edu.espe.GrupoInvestigacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "UZITGDOCUMENT", schema = "UTIC")
public class ModelDocument {

    @Id
    @GeneratedValue(generator = "UZITGDOCUMENT_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGDOCUMENT_Sequence", sequenceName = "UZISGDOCUMENT")
    @Column(name = "UZITGDOCUMENT_ID")
    private Long id;

    @Column(name = "UZITGDOCUMENT_NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "UZITGDOCUMENT_DESCRIPTION", length = 500)
    private String description;

    @Column(name = "UZITGDOCUMENT_STATUS", nullable = false)
    private String status;

    @Column(name = "UZITGDOCUMENT_USER_CREATE", nullable = false, length = 50)
    private String userCreate;

    @Column(name = "UZITGDOCUMENT_DATE_CREATE", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "UZITGDOCUMENT_USER_MODIFICATE", length = 50)
    private String userModificate;

    @Column(name = "UZITGDOCUMENT_DATE_MODIFICATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModificate;

    @OneToMany(mappedBy = "modelDocument")
    private List<ModelAnnexes> modelAnnexes;
}
