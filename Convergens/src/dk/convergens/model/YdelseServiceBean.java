package dk.convergens.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class YdelseServiceBean implements YdelseService {

	private List<Ydelse> ydelser = new ArrayList<Ydelse>();

	@PostConstruct
	public void postConstruct() {
		ydelser.add(new Ydelse(1, "2711900000", 199, "01-01-2014", "Ydelse1"));
		ydelser.add(new Ydelse(2, "2711900000", 99, "01-01-2013", "Ydelse2"));
		ydelser.add(new Ydelse(3, "0109720000", 10, "01-01-2012", "Ydelse1"));
		ydelser.add(new Ydelse(4, "1007680000", 50, "01-01-2014", "Ydelse3"));
		ydelser.add(new Ydelse(5, "1511530000", 200, "01-01-2012", "Ydelse4"));
		ydelser.add(new Ydelse(6, "2004660000", 90, "01-01-2013", "Ydelse3"));
		ydelser.add(new Ydelse(7, "2905780000", 200, "01-01-2014", "Ydelse2"));
		ydelser.add(new Ydelse(8, "0312830000", 1000, "01-01-2014", "Ydelse5"));
		System.out.println("Ydelser created.");
	}

	@Override
	public <T> T create(T t) {
		ydelser.add((Ydelse) t);
		return (T) t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T find(Class<T> type, Object id /* id = cpr */) {

		List<Ydelse> y = new ArrayList<Ydelse>();

		for (Ydelse ydelse : ydelser)
			if (ydelse.getCpr().equals(id))
				y.add(ydelse);

		if (!y.isEmpty())
			return (T) y;
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T find() {
		if (!ydelser.isEmpty())
			return (T) ydelser;
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T find(Class<T> t, Object type, Object cpr) {

		List<Ydelse> y = new ArrayList<Ydelse>();

		for (Ydelse ydelse : ydelser)
			if (ydelse.getType().equals(type) && ydelse.getCpr().equals(cpr))
				y.add(ydelse);

		if (!y.isEmpty())
			return (T) y;
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T update(T t) {

		Ydelse y = (Ydelse) t;

		for (Ydelse ydelse : ydelser)
			if (ydelse.getId() == y.getId()) {

				ydelse.setCpr(y.getCpr());
				ydelse.setDato(y.getDato());
				ydelse.setKr(y.getKr());
				ydelse.setType(y.getType());

				return (T) ydelse;
			}

		return null;
	}

	@Override
	public void delete(Class type, Object id) {

		for (Ydelse ydelse : ydelser) {
			if (ydelse.getId() == (Integer) id) {
				ydelser.remove(ydelse);
				break;
			}
		}
	}

}
