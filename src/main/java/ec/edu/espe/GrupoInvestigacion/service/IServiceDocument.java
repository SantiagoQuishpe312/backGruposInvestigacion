package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDocument;

import java.util.List;

public interface IServiceDocument {
    public List<DtoDocument> findAll();

    public DtoDocument find(Long id);
    Long save(DtoDocument dtoDocument);
    void update(DtoDocument dtoDocument);
}
