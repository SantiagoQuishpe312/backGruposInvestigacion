package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.List;

@Data
public class DtoClosureGetAllData {
    private DtoClosure InformeCierre;
    private DtoClosureRequest SolicitudCierreGI;
    private DtoUnsatisfactory EvInsatisfactoria;
}
