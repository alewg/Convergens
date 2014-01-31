package dk.convergens.ydelse.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;

import org.jboss.resteasy.spi.NotImplementedYetException;

/**
 * Implementing YdelseService with an EntityManager for CRUD-services.
 */

@Stateless
public class YdelseServiceMySQLBean implements YdelseService {

	/**
	 * Logger
	 */
	private static Logger logger = Logger.getLogger(YdelseServiceMySQLBean.class.getName());

	/**
	 * Persistence unit.
	 */
	@PersistenceContext
	EntityManager em;

	@PostConstruct
	public void init() {
	}

	/**
	 * Return persisted entity. Makes sure that entity is in Entity Manager and
	 * in Database.
	 * 
	 * @param t
	 *            Object T.
	 * 
	 * @return T The created object.
	 * @throw {@link EJBException}
	 */
	@Override
	public <T> T create(T t) {
		logger.fine("Creating ydelse: " + t);

		try {

			// Put entity in manager
			em.persist(t);
			// Persist entity manger
			em.flush();
			// Make entity manager and database consistent.
			em.refresh(t);

		} catch (EJBException ejbe) {
			throw (EJBException) new EJBException().initCause(ejbe);
		}

		// Return persisted entity
		return t;
	}

	/**
	 * Finds an entity based on id.
	 * 
	 * @param type
	 *            Takes an "entity".class.
	 * @param id
	 *            Long.
	 * 
	 * @return Returns an entity <T>.
	 */
	@Override
	public <T> T find(Class<T> type, Object id) {
//		logger.info("Searching " + type + " with id: " + id);
//		return em.find(type, id);
		throw new NotImplementedYetException();
	}

	/**
	 * Update entity. Return updated entity if successful. Return
	 * EntityNotFoundException If entity NOT exist.
	 * 
	 * @param t
	 *            Takes and entity.
	 * 
	 * @return Returns the updated entity.
	 * 
	 * @throw EnityNotFoundException
	 */
	@Override
	public <T> T update(T t) {
		logger.fine("Updating " + t);

		try {
			
			// Check if entity not exist.
			if (em.getReference(t.getClass(), ((Ydelse) t).getId()) == null) {
				throw new EntityNotFoundException();
			}
			
			return em.merge(t);
			
		} catch (EJBException ejbe) {
			throw (EJBException) new EJBException().initCause(ejbe);
		}
	}

	/**
	 * 
	 * Deleting an entity.
	 * 
	 * @param type
	 *            Takes an "entity".class.
	 * @param id
	 *            Long.
	 * 
	 */
	@Override
	public <T> void delete(Class<T> type, Object id) {
		
		logger.fine("Delete entity");
		
		Object obj = em.getReference(type, id);
		
		if(obj==null)
			throw new EntityNotFoundException();

		try {
			em.remove(obj);
		} catch (EJBException ejbe) {
			throw (EJBException) new EJBException().initCause(ejbe);
		}

	}

	/**
	 * Return List<T> if list is not empty. If List<T> is empty throw
	 * NotFoundException.
	 * 
	 * @param namedQueryName
	 *            Name of the query created. (@NamedQuery).
	 * 
	 * @return Return the results generated from the query.
	 * 
	 * @throw NotFoundException
	 * @throw EJBException
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findWithNamedQuery(String namedQueryName) {
		logger.fine("Query: " + namedQueryName);

		List<T> results = null;

		try {
			results = em.createNamedQuery(namedQueryName).getResultList();

			if (results.isEmpty()) {
				throw new NotFoundException();
			}

		} catch (EJBException ejbe) {
			throw (EJBException) new EJBException().initCause(ejbe);
		}

		return results;
	}

	/**
	 * Returns List<T> if list is not empty. If list is empty throw
	 * NotFoundException.
	 * 
	 * @param namedQueryName
	 *            Name of the query created. (@NamedQuery).
	 * @param parameters
	 *            Sets parameters/variables for your query.
	 * 
	 * @return Return result list
	 * 
	 * @throw NotFoundException
	 * @throw EJBException
	 * 
	 */
	@Override
	public <T> List<T> findWithNamedQuery(String namedQueryName, Map<String, Object> parameters) {

		logger.fine("Query: " + namedQueryName + ", paramaters: " + parameters);

		try {

			List<T> results = findWithNamedQuery(namedQueryName, parameters, 0);

			if (results.isEmpty()) {
				throw new NotFoundException();
			}

			return results;

		} catch (EJBException ejbe) {
			throw (EJBException) new EJBException().initCause(ejbe);
		}

	}

	/**
	 * Find with a own query.
	 * 
	 * @param namedQueryName
	 *            Name of the query created. (@NamedQuery).
	 * @param parameters
	 *            Sets parameters/variables for your query.
	 * @param resultLimit
	 *            Sets a result limit for your query.
	 * 
	 * @return Return the results generated from the query.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findWithNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0)
			query.setMaxResults(resultLimit);
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

}
