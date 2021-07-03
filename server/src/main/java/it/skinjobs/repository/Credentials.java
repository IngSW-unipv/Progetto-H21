package it.skinjobs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.skinjobs.model.Credential;

/**
 * @author Jessica Vecchia
 */

/**
 * The query of this repository interface finds Credential by name. It is used within the
 * CredentialAPI login method because the first input to check from frontEnd is the inserted
 * name and then the related password.
 */

public interface Credentials extends CrudRepository<Credential, Integer>{
    @Query("FROM Credential WHERE name = ?1")
    List<Credential> findByName(String name);
}