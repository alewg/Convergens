package dk.convergens.model;

public interface YdelseService {

	public <T> T create(T t);

	public <T> T find(Class<T> type, Object id);
	
	//------- Delete later -------------
	public <T> T find();
	public <T> T find(Class<T> t, Object type, Object cpr);
	//-----------------------------------------------------
	
	public <T> T update(T t);

	public void delete(Class type, Object id);

}
