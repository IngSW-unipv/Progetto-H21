package it.skinjobs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.skinjobs.model.Session;

/**
 * @author Jessica Vecchia
 */

/**
 * This query is necessary each time it's necessary to check if a session is valid by finding it
 * for the related token saved within the DB.
 */
public interface Sessions extends CrudRepository<Session, Integer>{
    @Query("FROM Session s WHERE token = ?1 order by s.expireDate desc")
    List<Session> findByToken(String token);
}
