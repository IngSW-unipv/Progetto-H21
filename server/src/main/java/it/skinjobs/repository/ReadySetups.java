package it.skinjobs.repository;

import org.springframework.data.repository.CrudRepository;

import it.skinjobs.model.ReadySetup;

/**
 *
 * @author Andrei Blindu
 * @author Filippo Maria Rognoni
 * @author Jessica Vecchia
 */

/**
 * Repository interface using ReadySetup. This interface will be turned into a class by Hibernate tool at runtime
 * The object coming from this interface is the column(set of rows) of the DB
 */

public interface ReadySetups extends CrudRepository<ReadySetup, Integer>{
    
}