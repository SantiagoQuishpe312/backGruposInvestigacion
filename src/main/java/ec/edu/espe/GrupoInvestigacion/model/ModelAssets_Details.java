package ec.edu.espe.GrupoInvestigacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "UZITGASSETS_DETAILS", schema = "UTIC")
public class ModelAssets_Details {
    @Id
    @GeneratedValue(generator = "UZITGASSETS_DETAILS_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZISGASSETS_DETAILS_Sequence", sequenceName = "UZISGASSETS_DETAILS")
    @Column(name = "UZITGASSETS_DETAILS_ID")
    private Long id;

    @Column(name = "UZITGASSETS_DETAILS_DESCRIPTION")
    private String description;

    @Column(name = "UZITGASSETS_DETAILS_CODE")
    private String code;

    @Column(name = "UZITGASSETS_DETAILS_ADQUISITION_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.DATE)
    private Date adquisitionDate;

    @Column(name = "UZITGASSETS_DETAILS_CURRENT_STATUS")
    private String currentStatus;

    @Column(name = "UZITGASSETS_DETAILS_CURRENT_LOCATION")
    private String currentLocation;

    @Column(name = "UZITGASSETS_DETAILS_USER_CREATE")
    private String userCreate;

    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGASSETS_DETAILS_DATE_CREATE")
    private Date dateCreate;

    @Column(name = "UZITGASSETS_DETAILS_USER_MODIFICATE")
    private String userModificate;

    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UZITGASSETS_DETAILS_DATE_MODIFICATE")
    private Date dateModificate;

    @ManyToOne
    @JoinColumn(name ="UZITGASSETS_REPORT_ID", nullable = false)
    private ModelAssetsReport modelAssetsReport;

}
