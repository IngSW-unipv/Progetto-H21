package it.skinjobs.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.skinjobs.utils.Callable;

/**
 *
 * @author Jessica Vecchia
 */

/**
 *
 * @param <T>
 * @param <U>
 * @param <I>
 *
 * This class contains the basic operations that are necessary to be done before the admin manipulates database under authentication
 */
public abstract class BaseAPI<T, U, I> implements API<T, U, I> {

    @Autowired
    protected CredentialAPI credentialAPI;
    /**
     *
     * @param headers
     * @param dto
     * @param callable
     * @return ResponseEntity
     *
     * If the token is valid for that session, the operation can be done. Otherwise it cannot be completed.
     */
    public ResponseEntity<T> sessionOperation(Map<String, String> headers, U dto,
            Callable<ResponseEntity<T>, U> callable) {
        String token = headers.get("token");  
        if (credentialAPI.sessionIsValid(token)) {
            return callable.call(dto);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     *
     * @param headers
     * @param id
     * @return ResponseEntity
     *
     * If the token is valid for that session, the delete operation can be done. Otherwise it cannot be completed.
     * For design reasons a separate method for delete operation is necessary.
     */
    
    public ResponseEntity<Boolean> sessionDeleteOperation(Map<String, String> headers, Integer id) {
        String token = headers.get("token");  
        if (credentialAPI.sessionIsValid(token)) {
            if (deleteEntity(id)) {
                return new ResponseEntity<>(true, HttpStatus.OK);
             } else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
             }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }    

    protected abstract Boolean deleteEntity(Integer index);



}
