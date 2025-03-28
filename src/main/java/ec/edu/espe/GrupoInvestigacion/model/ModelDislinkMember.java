package ec.edu.espe.GrupoInvestigacion.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.util.Date;
@Data
@ToString
@Entity
@Table(name = "UZITGDISLINK_MEMBER", schema = "UTIC")

public class ModelDislinkMember {
    @EmbeddedId
    private ModelDislinkMemberId id;
    @ManyToOne
    @JoinColumn(name = "UZITGUSER_ID", insertable = false, updatable = false)
    private ModelUser modelUser;
    @ManyToOne
    @JoinColumn(name = "UZITGINV_GROUP_ID", insertable = false, updatable = false)
    private ModelInvGroup modelInvGroup;
    @Column(name = "UZITGDISLINK_MEMBER_USER_CREATE")
    private String userCreate;

    @Column(name = "UZITGDISLINK_MEMBER_TYPE")
    private String type;

    @Column(name = "UZITGDISLINK_MEMBER_LINK_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.DATE)
    private String linkDate;


    @Column(name = "UZITGDISLINK_MEMBER_STATUS")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGDISLINK_MEMBER_DATE_CREATE")
    private Date dateCreate;

    @Column(name = "UZITGDISLINK_MEMBER_USER_MODIFICAT")
    private String userModificate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGDISLINK_MEMBER_DATE_MODIFICAT")
    private Date dateModificate;
}
