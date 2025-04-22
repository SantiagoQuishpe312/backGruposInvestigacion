package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.List;

@Data
public class DtoLinkGetData {
    private DtoLink formularioVinculacion;
    private DtoInvGroupGetData grupoInvestigacion;
    private DtoUser nuevoInvestigador;

}
