package it.skinjobs.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.skinjobs.utils.Callable;

/**
 *
 * @param <T>
 * @param <U>
 * @param <I>
 *
 * This class allows admin to refresh the database.
 */
public abstract class DeleteAllAPI<T, U, I> extends BaseAPI<T, U, I> {

   public ResponseEntity<Boolean> sessionDeleteAllOperation(Map<String, String> headers, 
            Callable<ResponseEntity<Boolean>, U> callable) {
        String token = headers.get("token");  
        if (credentialAPI.sessionIsValid(token)) {
            return callable.call(null);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
