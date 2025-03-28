package ec.edu.espe.GrupoInvestigacion.model;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "UZITGCLOSURE_REQUEST", schema = "UTIC")
public class ModelClosureRequest {

    @Id
    @GeneratedValue(generator = "UZITGCLOSURE_REQUEST_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", name = "UZITGCLOSURE_REQUEST_SEQ", sequenceName = "UZISGCLOSURE_REQUEST", allocationSize = 1)
    @Column(name = "UZITGCLOSURE_REQUEST_ID")
    private Long id;

    @Lob
    @Column(name = "UZITGCLOSURE_REQUEST_MOTIVATIO", columnDefinition = "CLOB")
    private String motivation;

    @Lob
    @Column(name = "UZITGCLOSURE_REQUEST_JUSTIFICA", columnDefinition = "CLOB")
    private String justification;

    @Column(name = "UZITGCLOSURE_REQUEST_USER_CREA", length = 50)
    private String userCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGCLOSURE_REQUEST_DATE_CREA")
    private Date dateCreate;

    @Column(name = "UZITGCLOSURE_REQUEST_USER_MODI", length = 50)
    private String userModificate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGCLOSURE_REQUEST_DATE_MODI")
    private Date dateModificate;

    @ManyToOne
    @JoinColumn(name = "UZITGCLOSURE_ID", nullable = false)
    private ModelClosure modelClosure;
}