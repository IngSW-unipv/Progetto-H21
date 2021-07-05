package it.skinjobs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.skinjobs.model.CompatibilityConstraint;

/**
 *
 * @author Andrei Blindu
 * @author Filippo Maria Rognoni
 * @author Jessica Vecchia
 */

/**
 * Repository interface using CompatibilityConstraint. This interface will be turned into a class by Hibernate tool at runtime
 * The object coming from this interface is the column(set of rows) of the DB
 */
public interface CompatibilityConstraints extends CrudRepository<CompatibilityConstraint, Integer>{
    
   @Query("FROM CompatibilityConstraint WHERE family_id1 = ?1")  
   List<CompatibilityConstraint> findByFamilyId(Integer familyId);

}
