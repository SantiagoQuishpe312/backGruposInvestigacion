package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoExhibit;

import java.util.List;

public interface IServiceExhibit {
    public List<DtoExhibit> findAll();

    public DtoExhibit find(Long id);

    Long create(DtoExhibit dtoExhibit);
    void update(DtoExhibit dtoExhibit);
}
