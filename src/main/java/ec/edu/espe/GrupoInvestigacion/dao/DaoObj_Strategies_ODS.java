package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelInstStrategicObj;
import ec.edu.espe.GrupoInvestigacion.model.ModelMagazines;
import ec.edu.espe.GrupoInvestigacion.model.ModelObj_Strategies_ODS;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DaoObj_Strategies_ODS extends CrudRepository<ModelObj_Strategies_ODS, Long>  {
    @Query(value = "SELECT oso FROM ModelObj_Strategies_ODS oso WHERE oso.modelSpecificObjectives.id=:id")
    public Optional<List<ModelObj_Strategies_ODS>> findByObjEnable(Long id);
}
