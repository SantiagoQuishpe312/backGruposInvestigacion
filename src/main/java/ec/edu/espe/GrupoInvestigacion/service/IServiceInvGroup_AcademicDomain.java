package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup_AcademicDomain;
import ec.edu.espe.GrupoInvestigacion.dto.DtoReqGetAcademicDom;

import java.util.List;

public interface IServiceInvGroup_AcademicDomain {
    public List<DtoInvGroup_AcademicDomain> findAll();
    public List<DtoInvGroup_AcademicDomain> find(Long id);
    Long save(DtoInvGroup_AcademicDomain dtoAcadCrea);
   // public DtoReqGetAcademicDom findByReq(Long id);
}
