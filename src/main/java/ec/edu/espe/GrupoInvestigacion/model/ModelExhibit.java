package ec.edu.espe.GrupoInvestigacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "UZITGEXHIBIT", schema = "UTIC")
public class ModelExhibit {

    @Id
    @GeneratedValue(generator = "UZITGEXHIBIT_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGEXHIBIT_Sequence", sequenceName = "UZISGEXHIBIT")
    @Column(name = "UZITG_EXHIBIT_ID")
    private Long id;
    @Column(name = "UZITG_EXHIBIT_NAME", nullable = false)
    private String name;

    @Column(name = "UZITG_EXHIBIT_ROUTE", nullable = false)
    private String route;

    @Column(name = "UZITG_EXHIBIT_USER_CREATE")
    private String userCreate;

    @Column(name = "UZITG_EXHIBIT_DATE_CREATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "UZITG_EXHIBIT_USER_MODIFICATE")
    private String userModificate;

    @Column(name = "UZITG_EXHIBIT_DATE_MODIFICATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModificate;
    @ManyToOne
    @JoinColumn(name = "UZITG_CREATION_REQ_ID")
    private ModelCreationReq modelCreationReq;

}