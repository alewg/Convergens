package dk.convergens.ydelse.model;

import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;

/**
 * 
 * Interface for creating generic RESTful CRUD Service.
 *
 */

public interface YdelseService {

	/**
	 * Creates an entity.
	 * 
	 * @param t An entity.
	 * @return Returns the created entity.
	 */
	public <T> T create(T t);

	/**
	 * 
	 * Finds an entity.
	 * 
	 * @param type The entity class.
	 * @param id The primary key for entity class.
	 * @return Returns an entity class.
	 */
	public <T> T find(Class<T> type, Object id);

	/**
	 * Finds an list of result based on namedQuery.
	 * 
	 * @param namedQueryName The name of the created namedQuery.
	 * @return Return a result list.
	 */
	public <T> List<T> findWithNamedQuery(String namedQueryName);

	/**
	 * Finds an list of result based on namedQuery.
	 * 
	 * @param namedQueryName The name of the created namedQuery.
	 * @param parameters The parameters/variables for the namedQuery.
	 * 
	 * @return Return a result list.
	 */
	public <T> List<T> findWithNamedQuery(String namedQueryName, Map<String, Object> parameters);
	
	/**
	 * Finds an list of result based on namedQuery.
	 * 
	 * @param namedQueryName - The name of the created namedQuery.
	 * @param parameters The parameters/variables for the namedQuery.
	 * @param resultLimit The limit of the namedQuery.
	 * 
	 * @return Return a result list.
	 */
	public <T> List<T> findWithNamedQuery(String namedQueryName, Map<String,Object> parameters,int resultLimit);

	
	/**
	 * Updating entity.
	 * 
	 * @param t An entity.
	 * @return Returns the updated entity.
	 */
	public <T> T update(T t);

	/**
	 * 
	 * Deletes an entity from id.
	 * 
	 * @param type The entity class.
	 * @param id Long.
	 */
	public <T> void delete(Class<T> type, Object id);

}
