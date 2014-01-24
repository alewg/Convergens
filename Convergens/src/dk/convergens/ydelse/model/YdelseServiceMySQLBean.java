package dk.convergens.ydelse.model;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class YdelseServiceMySQLBean implements YdelseService {
	
	//@PersistenceContext
	//EntityManager em;
	
	@PostConstruct
	public void init() {
		
	}
	
	@Override
	public <T> T create(T t) {
		//TODO Auto-generated method stub - em.persist(t);
		return null;
	}

	@Override
	public <T> T find(Class<T> type, Object id) {
		
		
		return null;
	}

	@Override
	public <T> T find() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T find(Class<T> t, Object type, Object cpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T update(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Class type, Object id) {
		// TODO Auto-generated method stub
		
	}

}
