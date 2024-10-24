package ec.edu.espe.GrupoInvestigacion.service;


import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup_Line;

import java.util.List;

public interface IServiceInvGroup_Line {
    public List<DtoInvGroup_Line> findAll();

    public List<DtoInvGroup_Line> find(Long id);
    Long save(DtoInvGroup_Line dtoLineCrea);
   // public DtoReqGetLine findByReq(Long id);
}
