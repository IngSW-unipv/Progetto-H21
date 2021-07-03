package it.skinjobs.utils;

/**
 * @author Jessica Vecchia
 * 
 * This Object is created in order to implement the baseApi method sessionOperation (checking if
 * a session is valid). Taking inspiration from the Strategy Pattern the code is structured to make
 * this check once for all the APIs performing operations under authentication.
 *
 * @param <A>
 * @param <B>
 */
public interface Callable<A, B> {

    A call(B input);
}
