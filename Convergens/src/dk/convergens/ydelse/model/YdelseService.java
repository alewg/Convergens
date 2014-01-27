package dk.convergens.ydelse.model;

import java.util.List;
import java.util.Map;

public interface YdelseService {

	public <T> T create(T t);

	public <T> T find(Class<T> type, Object id);

	public <T> List<T> findWithNamedQuery(String namedQueryName);

	public <T> List<T> findWithNamedQuery(String namedQueryName, Map<String, Object> parameters);
	
	public <T> List<T> findWithNamedQuery(String namedQueryName, Map<String,Object> parameters,int resultLimit);

	public <T> T update(T t);

	public <T> void delete(Class<T> type, Object id);

}
