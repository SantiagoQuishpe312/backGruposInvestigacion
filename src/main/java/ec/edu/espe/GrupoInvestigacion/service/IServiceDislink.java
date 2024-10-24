package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoDislink;

import java.util.List;

public interface IServiceDislink {
    public List<DtoDislink> findAll();

    public DtoDislink find(Long id);
    Long save(DtoDislink dtoDislink);
    public void update(DtoDislink dtoDislink);
}
