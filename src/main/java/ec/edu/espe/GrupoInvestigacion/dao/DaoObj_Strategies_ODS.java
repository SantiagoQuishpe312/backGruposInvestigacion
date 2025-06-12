package ec.edu.espe.GrupoInvestigacion.dao;

import ec.edu.espe.GrupoInvestigacion.model.ModelInstStrategicObj;
import ec.edu.espe.GrupoInvestigacion.model.ModelMagazines;
import ec.edu.espe.GrupoInvestigacion.model.ModelObj_Strategies_ODS;
import ec.edu.espe.GrupoInvestigacion.model.ModelSpecificObjectives;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaoObj_Strategies_ODS extends CrudRepository<ModelObj_Strategies_ODS, Long>  {
    @Query(value = "SELECT oso FROM ModelObj_Strategies_ODS oso WHERE oso.modelSpecificObjectives.id=:id")
    public Optional<List<ModelObj_Strategies_ODS>> findByObjEnable(Long id);

    @Query(value = "SELECT oso FROM ModelObj_Strategies_ODS oso WHERE oso.modelSpecificObjectives.id=:id")
    public Optional<List<ModelObj_Strategies_ODS>> findByPlan(Long id);

    @Query(value = "SELECT oso FROM ModelObj_Strategies_ODS oso WHERE oso.modelSpecificObjectives.modelDevelopmentPlan.id=:id")
    public Optional<List<ModelObj_Strategies_ODS>> findByPlanRelations(Long id);

    @Query("SELECT o FROM ModelSpecificObjectives o where o.modelDevelopmentPlan.id = :planId")
    Optional<List<ModelSpecificObjectives>> findOdsObjByPlan(@Param("planId") Long planId);

    @Query("SELECT o FROM ModelSpecificObjectives o where o.modelDevelopmentPlan.id = :planId")
    Optional<List<ModelSpecificObjectives>> findOdsObjByPlanEnable(@Param("planId") Long planId);
    @Query("SELECT o FROM ModelSpecificObjectives o WHERE o.id=:id")
    Optional <ModelSpecificObjectives> findByObj(@Param("id") Long id);

    @Query ("SELECT o FROM ModelObj_Strategies_ODS o WHERE o.modelSpecificObjectives.id=:idObj AND o.modelStrategies.id=:idStrategy AND o.modelOds.id=:idOds")
    Optional <ModelObj_Strategies_ODS> findEnable(@Param("idObj") Long idObj, @Param("idStrategy") Long idStrategy,@Param("idOds")Long idOds);


    @Query("SELECT o FROM ModelObj_Strategies_ODS o WHERE o.modelSpecificObjectives.modelDevelopmentPlan.id = :id")
    Optional<List<ModelObj_Strategies_ODS>> findBySpecificObjective(@Param("id") Long specificObjectiveId);
    @Query("SELECT o FROM ModelObj_Strategies_ODS o WHERE o.modelSpecificObjectives.id = :id")
    Optional<List<ModelObj_Strategies_ODS>> findBySpecificObjective2(@Param("id") Long specificObjectiveId);
}
