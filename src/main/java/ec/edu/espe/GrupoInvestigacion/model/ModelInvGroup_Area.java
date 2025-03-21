package ec.edu.espe.GrupoInvestigacion.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "UZITGINV_AREA", schema = "UTIC")

public class ModelInvGroup_Area {
    @EmbeddedId
    private ModelInvGroup_Area_Id id;

    @Column(name = "UZITGINV_AREA_USER_CREATE")
    private String userCreate;

    @Column(name = "UZITGINV_AREA_DATE_CREATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "UZITGINV_AREA_USER_MODIFICATE")
    private String userModificate;

    @Column(name = "UZITGINV_AREA_DATE_MODIFICATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModificate;

    @ManyToOne
    @JoinColumn(name = "UZITGAREA_ID", insertable = false, updatable = false)
    private ModelArea modelArea;
    @ManyToOne
    @JoinColumn(name = "UZITGINV_GROUP_ID", insertable = false, updatable = false)
    private ModelInvGroup modelInvGroup;

}
