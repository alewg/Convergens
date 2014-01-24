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
import dk.convergens.model.YdelseService;
import dk.convergens.model.YdelseServiceBean;

@Path("/ydelser/")
public class YdelserREST {

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
		return ydelseService.find();
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
		return (List<Ydelse>) ydelseService.find(Ydelse.class, cpr);
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
	public List<Ydelse> getYdelserByTypeAndCPR(@PathParam("type") String type,
			@PathParam("cpr") String cpr) {
		return (List<Ydelse>) ydelseService.find(Ydelse.class, type, cpr);
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

		// Bliver fixet i databasen
		int id = 0;

		Ydelse y = new Ydelse(id, cpr, kr, dato, type);
		ydelseService.create(y);
		return Response.created(URI.create("ydelser/" + id)).build();
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
	public Response update(@PathParam("id") int id,
			@FormParam("cpr") String cpr, @FormParam("kr") int kr,
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
	public void delete(@PathParam("id") Integer id) {
		ydelseService.delete(Ydelse.class, id);
	}

}
