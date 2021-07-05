package it.skinjobs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.skinjobs.model.ComponentType;

/**
 *
 * @author Andrei Blindu
 * @author Filippo Maria Rognoni
 * @author Jessica Vecchia
 */

/**
 * Repository interface using ComponentType. This interface will be turned into a class by Hibernate tool at runtime
 * The object coming from this interface is the column(set of rows) of the DB
 */
public interface ComponentTypes extends CrudRepository<ComponentType, Integer> {
    @Query("FROM ComponentType c ORDER BY c.sortOrder")
    List<ComponentType> findAllSorted();
}
