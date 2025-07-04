package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dto.DtoControlPanel;
import ec.edu.espe.GrupoInvestigacion.dto.DtoControlPanelGetData;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup;

import java.util.List;

public interface IServiceControlPanel {
    public List<DtoControlPanel> findAll();

    public DtoControlPanel find(Long id);
    public List<DtoControlPanel> findByDev(Long id);
    public List<DtoControlPanelGetData> findCompleteByDev(Long id);
    public DtoControlPanelGetData findAllById(Long id);
    public List<DtoControlPanel> findBySpecificObj(Long id);
    Long save(DtoControlPanel dtoControlPanel);
    void update(DtoControlPanel dtoControlPanel);
    void delete(Long id);
}
