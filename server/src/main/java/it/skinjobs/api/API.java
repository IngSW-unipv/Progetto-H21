package it.skinjobs.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;

/**
* @author Jessica Vecchia
*/

/**
*
* @param <T> Generic entity
* @param <U> Generic DTO
* @param <I> Generic primary key
*
* All the common API methods are here declared where T, U and I are templates indicating that the APIs will act on an
* entity object from the business logic(T), a DTO object(U) and a primary key index(I), but not showing specifically
* the actual class
*/
public interface API<T, U, I> {
   
   Iterable<T> getAll();
   ResponseEntity<T> newElement(Map<String, String> headers,U dto);
   ResponseEntity<T> updateElement(Map<String, String> headers, U dto, I index);
   ResponseEntity<Boolean> deleteElement(Map<String, String> headers, I index);
   ResponseEntity<T> getById(I index);
}
