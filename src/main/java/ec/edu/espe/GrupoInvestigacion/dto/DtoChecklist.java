package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.Date;
@Data
public class DtoChecklist {
    private Long idChecklist;
    private Long idGrupoInv;
    private Date fechaChecklist;
    private Boolean peticionMemorando;
    private Boolean formularioGrInv;
    private Boolean planDevGr;
    private Boolean planEconomico;
    private Boolean hojaVida;
    private Boolean certificado;
    private String recibidoPor;
    private String enviadoPor;
    private String usuarioCreacionChecklist;
    private Date fechaCreacionChecklist;
    private String usuarioModificacionChecklist;
    private Date fechaModificacionChecklist;
}
