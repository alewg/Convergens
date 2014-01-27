package dk.convergens.ydelse.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import dk.convergens.ydelse.model.Ydelse;
import dk.convergens.ydelse.model.YdelseService;

@Path("/ydelser/")
public class YdelseResource {

	@Inject
	YdelseService ydelseService;

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
		return ydelseService.findWithNamedQuery("Ydelse.findAll");
	}

	@SuppressWarnings("unchecked")
	/**
	 *
	 * <p>
	 * Get resource by cpr
	 * </p>
	 *
	 * @param cpr
	 * the cpr number
	 * @return List of JSON objects, if none, return null/undefined
	 *
	 */
	@GET
	@Path("/{cpr}")
	@Produces("application/json")
	public List<Ydelse> getYdelserByCPR(@PathParam("cpr") String cpr) {

		Map param = new HashMap<>();

		param.put("cpr", cpr);

		return ydelseService.findWithNamedQuery("Ydelse.findByCPR", param);
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
	@SuppressWarnings("unchecked")
	@GET
	@Path("/{type}/{cpr}")
	@Produces("application/json")
	public List<Ydelse> getYdelserByTypeAndCPR(@PathParam("type") String type, @PathParam("cpr") String cpr) {

		Map param = new HashMap<>();

		param.put("type", type);
		param.put("cpr", cpr);

		return ydelseService.findWithNamedQuery("Ydelse.findByTypeAndCPR", param);
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
	public Response add(@FormParam("createCPR") String cpr, @FormParam("createKr") int kr,
			@FormParam("createDato") String dato, @FormParam("createType") String type) {

		// create id
		Long id = 0L;

		Ydelse y = ydelseService.create(new Ydelse(id, cpr, kr, dato, type));

		return Response.created(URI.create("ydelser/" + y.getId())).build();
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
	 * @return if updated return ok, if created return response with the new
	 *         location
	 */
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, @FormParam("cpr") String cpr, @FormParam("kr") int kr,
			@FormParam("dato") String dato, @FormParam("type") String type) {

		Ydelse y = new Ydelse(id, cpr, kr, dato, type);

		return Response.ok(ydelseService.update(y)).build();
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
	public void delete(@PathParam("id") Long id) {
		ydelseService.delete(Ydelse.class, id);
	}

}
