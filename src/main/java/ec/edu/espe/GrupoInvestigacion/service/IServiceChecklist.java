package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoChecklist;
import ec.edu.espe.GrupoInvestigacion.dto.DtoGroupRegForm;

import java.util.List;

public interface IServiceChecklist {
    public List<DtoChecklist> findAll();
    public DtoChecklist find(Long id);
    public DtoChecklist findByGroup(Long id);
    Long save(DtoChecklist dtoChecklist);
    void update(DtoChecklist dtoChecklist);


}
