package ec.edu.espe.GrupoInvestigacion.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "UZITGCLOSURE")
public class ModelClosure {
    @Id
    @GeneratedValue(generator = "UZITGCLOSURE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "UTIC", allocationSize = 1, name = "UZITGCLOSURE_Sequence", sequenceName = "UZISGCLOSURE")
    @Column(name = "UZITGCLOSURE_ID")
    private Long id;

    @Column(name = "UZITG")
}
