package dk.convergens.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

import dk.convergens.ydelse.Ydelse;

@Path("/ydelser/")
public class YdelserREST {

	// Gson for easy json output
	Gson gson = new Gson();

	// List of all ydelser
	private List<Ydelse> ydelser = null;

	public YdelserREST() {

		if (ydelser == null) {
			ydelser = new ArrayList<Ydelse>();
		}

		ydelser.add(new Ydelse(1, "2711900000", 199, "01-01-2014", "Ydelse1"));
		ydelser.add(new Ydelse(2, "2711900000", 99, "01-01-2013", "Ydelse2"));
		ydelser.add(new Ydelse(3, "0109720000", 10, "01-01-2012", "Ydelse1"));
		ydelser.add(new Ydelse(4, "1007680000", 50, "01-01-2014", "Ydelse3"));
		ydelser.add(new Ydelse(5, "1511530000", 200, "01-01-2012", "Ydelse4"));
		ydelser.add(new Ydelse(6, "2004660000", 90, "01-01-2013", "Ydelse3"));
		ydelser.add(new Ydelse(7, "2905780000", 200, "01-01-2014", "Ydelse2"));
		ydelser.add(new Ydelse(8, "0312830000", 1000, "01-01-2014", "Ydelse5"));

	}

	@GET
	@Produces("application/json")
	public String getAll() {
		return gson.toJson(ydelser);
	}

	@GET
	@Path("/{cpr}")
	@Produces("application/json")
	public List<Ydelse> getYdelserByCPR(@PathParam("cpr") String cpr) {

		List<Ydelse> ydelserByCPR = new ArrayList<Ydelse>();

		for (Ydelse ydelse : ydelser)
			if (ydelse.getCpr().equals(cpr))
				ydelserByCPR.add(ydelse);

		return ydelserByCPR;
	}

	@GET
	@Path("/{type}/{cpr}")
	@Produces("application/json")
	public List<Ydelse> getYdelserByTypeAndCPR(@PathParam("type") String type,
			@PathParam("cpr") String cpr) {

		List<Ydelse> ydelserByTypeAndCPR = new ArrayList<Ydelse>();

		for (Ydelse ydelse : ydelser)
			if (ydelse.getType().equals(type) && ydelse.getCpr().equals(cpr))
				ydelserByTypeAndCPR.add(ydelse);

		return ydelserByTypeAndCPR;
	}

	/*
	 * @POST public void add(@FormParam("createID") int id,
	 * 
	 * @FormParam("createCPR") String cpr,
	 * 
	 * @FormParam("createKr") int kr,
	 * 
	 * @FormParam("createDato") String dato,
	 * 
	 * @FormParam("createType") String type) {
	 * 
	 * ydelser.add(new Ydelse(id, cpr, kr, dato, type));
	 * 
	 * for(Ydelse y : ydelser) System.out.println(y);
	 * 
	 * }
	 */

	@POST
	public void add(@FormParam("createCPR") String cpr,
			@FormParam("createKr") int kr,
			@FormParam("createDato") String dato,
			@FormParam("createType") String type) {

		ydelser.add(new Ydelse(cpr, kr, dato, type));

		for (Ydelse ydelse : ydelser) {
			System.out.println(ydelse);
		}
	}

	@PUT
	@Path("/{id}")
	public void update(@PathParam("id") int id, @FormParam("cpr") String cpr,
			@FormParam("kr") int kr, @FormParam("dato") String dato,
			@FormParam("type") String type) {
		
		boolean found = false;

		for (Ydelse ydelse : ydelser) {
			if (ydelse.getId() == id) {
				ydelse.setCpr(cpr);
				ydelse.setDato(dato);
				ydelse.setKr(kr);
				ydelse.setType(type);
				found = true;
			}
		}
		
		if(!found)
			ydelser.add(new Ydelse(id, cpr, kr, dato, type));

	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") int id) {

		for (Ydelse ydelse : ydelser) {
			if (ydelse.getId() == id) {
				ydelser.remove(ydelse);
				break;
			}
		}

	}

}
