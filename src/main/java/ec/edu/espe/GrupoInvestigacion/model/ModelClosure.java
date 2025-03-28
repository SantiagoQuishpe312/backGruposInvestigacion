package ec.edu.espe.GrupoInvestigacion.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "UZITGCLOSURE", schema = "UTIC")
public class ModelClosure {
    @Id
    @GeneratedValue(generator = "UZITGCLOSURE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGCLOSURE_Sequence", sequenceName = "UZISGCLOSURE")
    @Column(name = "UZITGCLOSURE_ID")
    private Long id;

    @Column(name = "UZITGCLOSURE_OBJ", length = 200)
    private String obj;

    @Lob
    @Column(name = "UZITGCLOSURE_CONTEXT", columnDefinition = "CLOB")
    private String context;

    @Lob
    @Column(name = "UZITGCLOSURE_INITIAL_OBJ", columnDefinition = "CLOB")
    private String initialObj;

    @Lob
    @Column(name = "UZITGCLOSURE_ACTIVITIES", columnDefinition = "CLOB")
    private String activities;

    @Lob
    @Column(name = "UZITGCLOSURE_ACAD_IMPACT", columnDefinition = "CLOB")
    private String acadImpact;

    @Lob
    @Column(name = "UZITGCLOSURE_FIN_IMPACT", columnDefinition = "CLOB")
    private String finImpact;

    @Lob
    @Column(name = "UZITGCLOSURE_RELOCATION", columnDefinition = "CLOB")
    private String Relocation;

    @Lob
    @Column(name = "UZITGCLOSURE_CONCLUSION", columnDefinition = "CLOB")
    private String conclusion;

    @Lob
    @Column(name = "UZITGCLOSURE_RECOMENDATION", columnDefinition = "CLOB")
    private String recomendation;

    @Lob
    @Column(name = "UZITGCLOSURE_PROCESS", columnDefinition = "CLOB")
    private String process;

    @Column(name = "UZITGCLOSURE_USER_CREATE", length = 50)
    private String userCreate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGCLOSURE_DATE_CREATE")
    private Date dateCreate;

    @Column(name = "UZITGCLOSURE_USER_MODIFICATE", length = 50)
    private String userModificate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGCLOSURE_DATE_MODIFICATE")
    private Date dateModificate;

    @ManyToOne
    @JoinColumn(name="UZITGINV_GROUP_ID",nullable = false)
    private ModelInvGroup modelInvGroup;

    @OneToMany(mappedBy = "modelClosure")
    private List<ModelUnsatisfactory> modelUnsatisfactory;

    @OneToMany(mappedBy = "modelClosure")
    private List<ModelClosureRequest> modelClosureRequests;

}
