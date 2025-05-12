package ec.edu.espe.GrupoInvestigacion.dto;

import lombok.Data;

import java.util.List;

@Data
public class DtoInvMemberGetGroup {
    private List<DtoInvMember> miembro;
    private List<DtoInvGroup> grupo;
}
