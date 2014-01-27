package dk.convergens.ydelse.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class YdelseServiceMySQLBean implements YdelseService {

	@PersistenceContext
	EntityManager em;

	@PostConstruct
	public void init() {

	}

	@Override
	public <T> T create(T t) {
		em.persist(t);
		em.flush();
		return t;
	}

	@Override
	public <T> T find(Class<T> type, Object id) {
		return em.find(type, id);
	}

	@Override
	public <T> T update(T t) {
		return em.merge(t);
	}

	@Override
	public <T> void delete(Class<T> type, Object id) {
		Object obj = em.getReference(type, id);
		em.remove(obj);
	}

	@Override
	public List findWithNamedQuery(String namedQueryName) {
		return em.createNamedQuery(namedQueryName).getResultList();
	}

	@Override
	public List findWithNamedQuery(String namedQueryName, Map<String, Object> parameters) {
		return findWithNamedQuery(namedQueryName, parameters, 0);
	}

	@Override
	public List findWithNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
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
