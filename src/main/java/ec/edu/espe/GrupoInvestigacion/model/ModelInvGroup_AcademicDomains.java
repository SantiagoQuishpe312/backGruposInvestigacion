package ec.edu.espe.GrupoInvestigacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "UZITGINV_ACAD", schema = "UTIC")
public class ModelInvGroup_AcademicDomains {
    @EmbeddedId
    private ModelInvGroup_AcademicDomains_Id id;

    @Column(name = "UZITGINV_ACAD_USER_CREATE")
    private String userCreate;

    @Column(name = "UZITGINV_ACAD_DATE_CREATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "UZITGINV_ACAD_USER_MODIFICATE")
    private String userModificate;

    @Column(name = "UZITGINV_ACAD_DATE_MODIFICATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModificate;

    @ManyToOne
    @JoinColumn(name = "UZITGINV_GROUP_ID", insertable = false, updatable = false)
    private ModelInvGroup modelInvGroup;

    @ManyToOne
    @JoinColumn(name = "UZITGACADEMIC_DOM_ID", insertable = false, updatable = false)
    private ModelAcademicDomain modelAcademicDomain;
}
