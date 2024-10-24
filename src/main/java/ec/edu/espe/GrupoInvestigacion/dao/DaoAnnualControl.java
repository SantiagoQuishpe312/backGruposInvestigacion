package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelAnnualControl;
import ec.edu.espe.GrupoInvestigacion.model.ModelDeveLega;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DaoAnnualControl extends CrudRepository<ModelAnnualControl, Long> {
    @Query(value="SELECT ac FROM ModelAnnualControl ac WHERE ac.modelAnnualOperativePlan.id=:id")
    public Optional<List<ModelAnnualControl>> findByIdPlan(Long id);

    @Query(value="SELECT ac FROM ModelAnnualControl ac WHERE ac.modelControlPanel.id=:id")
    public Optional<List<ModelAnnualControl>> findByIdControl(Long id);

    @Query(value = "SELECT ac FROM ModelAnnualControl ac WHERE ac.modelControlPanel.id=:idPanel AND ac.modelAnnualOperativePlan.id=:idPlan ")    public Optional<List<ModelAnnualControl>> findByIdPanel(Long id);
    public Optional<ModelAnnualControl> findByIdControlPlan(Long idPanel,Long idPlan);



}
