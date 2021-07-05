package it.skinjobs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.skinjobs.model.Component;

/**
 * @author Andrei Blindu
 * @author Filippo Casarosa
 * @author Filippo Maria Rognoni
 * @author Jessica Vecchia
 */

/**
 * Repository interface using Component. This interface will be turned into a class by Hibernate tool at runtime
 * The object coming from this interface is the column(set of rows) of the DB
 */

public interface Components extends CrudRepository<Component, Integer> {

    @Query("SELECT c FROM Component c INNER JOIN c.componentFamily f INNER JOIN f.type n WHERE n.id = ?1")
    List<Component> findByTypeId(Integer typeId);

    @Query("FROM Component WHERE family_id = ?1")
    List<Component> findComponentsByFamilyId(Integer familyId);

}
