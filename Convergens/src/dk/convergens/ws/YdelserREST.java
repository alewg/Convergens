package dk.convergens.ws;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dk.convergens.model.Ydelse;
import dk.convergens.model.YdelseBean;

@Path("/ydelser/")
public class YdelserREST {
	
	@Inject
	YdelseBean ydelser;
	
	/**
	 * <p>
	 * Return all resources
	 * </p>
	 * 
	 * @return List of JSON objects, if none, return null/undefined
	 */
	@GET
	@Produces("application/json")
	public List<Ydelse> getAll() {
		
		if (ydelser.getYdelser().isEmpty())
			return null;
		else
			return ydelser.getYdelser();
	}

	/**
	 * 
	 * <p>
	 * Get resource by cpr
	 * </p>
	 * 
	 * @param cpr
	 *            the cpr number
	 * @return List of JSON objects, if none, return null/undefined
	 * 
	 */
	@GET
	@Path("/{cpr}")
	@Produces("application/json")
	public List<Ydelse> getYdelserByCPR(@PathParam("cpr") String cpr) {

		List<Ydelse> ydelserByCPR = new ArrayList<Ydelse>();

		for (Ydelse ydelse : ydelser.getYdelser())
			if (ydelse.getCpr().equals(cpr))
				ydelserByCPR.add(ydelse);

		if (ydelserByCPR.isEmpty())
			return null;
		else
			return ydelserByCPR;
	}

	/**
	 * <p>
	 * Get resource from type and cpr
	 * </p>
	 * 
	 * @param type
	 *            type of resource
	 * @param cpr
	 *            the cpr number
	 * @return List of JSON objects, if none, return null/undefined
	 * 
	 */
	@GET
	@Path("/{type}/{cpr}")
	@Produces("application/json")
	public List<Ydelse> getYdelserByTypeAndCPR(@PathParam("type") String type,
			@PathParam("cpr") String cpr) {

		List<Ydelse> ydelserByTypeAndCPR = new ArrayList<Ydelse>();

		for (Ydelse ydelse : ydelser.getYdelser())
			if (ydelse.getType().equals(type) && ydelse.getCpr().equals(cpr))
				ydelserByTypeAndCPR.add(ydelse);

		if (ydelserByTypeAndCPR.isEmpty())
			return null;
		else
			return ydelserByTypeAndCPR;
	}

	/**
	 * 
	 * <p>
	 * Add a new resource
	 * </p>
	 * 
	 * @param cpr
	 *            the cpr number
	 * @param kr
	 *            the price
	 * @param dato
	 *            the time of date
	 * @param type
	 *            type of resource
	 * @return response with location of the created resource
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@FormParam("createCPR") String cpr,
			@FormParam("createKr") int kr,
			@FormParam("createDato") String dato,
			@FormParam("createType") String type) {

		
		int id = 0;
		
		Ydelse y = new Ydelse(cpr, kr, dato, type);

		ydelser.getYdelser().add(y);

		return Response.created(URI.create("rest/ydelser/"+id)).build();
	}

	/**
	 * <p>
	 * Updating resource with provided parameters. If Id doesn't exist, new
	 * resource will be created. Path{/id}
	 * </p>
	 * 
	 * @param id
	 *            resource identificaiton
	 * @param cpr
	 *            the cpr number
	 * @param kr
	 *            the price
	 * @param dato
	 *            the time of date
	 * @param type
	 *            type of resource
	 * @return if updated return ok, if created return response with the new location
	 */
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, @FormParam("cpr") String cpr,
			@FormParam("kr") int kr, @FormParam("dato") String dato,
			@FormParam("type") String type) {

		boolean found = false;

		Ydelse y = null;

		for (Ydelse ydelse : ydelser.getYdelser()) {
			if (ydelse.getId() == id) {
				ydelse.setCpr(cpr);
				ydelse.setDato(dato);
				ydelse.setKr(kr);
				ydelse.setType(type);
				y = ydelse;
				found = true;
			}
		}

		if (!found) {
			y = new Ydelse(id, cpr, kr, dato, type);
			ydelser.getYdelser().add(y);
			return Response.created(URI.create("rest/ydelser/"+id)).build();
		} else {
			return Response.ok(y).build();
		}

	}

	/**
	 * <p>
	 * Deleting resource by id Path{/id}
	 * </p>
	 * 
	 * @param id
	 *            for deleting resource
	 * @return JSON object (the deleted resource)
	 * @throws NotFoundException
	 *             if deleting not existing resource
	 * 
	 */
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Ydelse delete(@PathParam("id") int id) {

		Ydelse y = null;
		boolean found = false;

		for (Ydelse ydelse : ydelser.getYdelser()) {
			if (ydelse.getId() == id) {
				y = ydelse;
				found = true;
				ydelser.getYdelser().remove(y);
				break;
			}
		}

		if (!found)
			throw new NotFoundException("Ydelse not found");

		return y;
	}

}
