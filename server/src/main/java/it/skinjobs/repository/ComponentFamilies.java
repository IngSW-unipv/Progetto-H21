package it.skinjobs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.skinjobs.model.ComponentFamily;

/**
 * @author Jessica Vecchia
 */

/**
 * Repository interface using ComponentFamily. This interface will be turned into a class by Hibernate tool at runtime
 * The object coming from this interface is the column(set of rows) of the DB
 */
public interface ComponentFamilies extends CrudRepository<ComponentFamily, Integer> {
    
    @Query("FROM ComponentFamily WHERE type_id = ?1")
    List<ComponentFamily> findComponentFamilyByTypeId(Integer typeId);
}
