package ec.edu.espe.GrupoInvestigacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "UZITGASSETS_DETAILS", schema = "UTIC")
public class ModelAssets_Details {
    @Id
    @GeneratedValue()
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZISGASSETS_DETAILS_Sequence", sequenceName = "UZISGASSETS_DETAILS")
    @Column()
    private Long id;

}
