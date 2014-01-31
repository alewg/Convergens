package dk.convergens.ydelse.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
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
import javax.ws.rs.core.Response.Status;

import dk.convergens.ydelse.exception.EntityNotFoundExceptionHandler;
import dk.convergens.ydelse.exception.PersistenceExceptionHandler;
import dk.convergens.ydelse.exception.NotFoundExceptionHandler;
import dk.convergens.ydelse.model.Ydelse;
import dk.convergens.ydelse.model.YdelseService;

/**
 * 
 * RESTful Web Service. It can execute methods depending on the ACTION and
 * METHOD.
 * 
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/ydelser/")
public class YdelseResource {

	/**
	 * Logger
	 */
	private static Logger logger = Logger.getLogger(YdelseResource.class.getName());

	/**
	 * Injecting interface
	 */
	@Inject
	YdelseService ydelseService;

	/**
	 * Inject NotFoundExceptionHandler
	 */
	@Inject
	NotFoundExceptionHandler nfeh;

	/**
	 * Inject PersistenceHandler
	 */
	@Inject
	PersistenceExceptionHandler peh;

	/**
	 * Inject EntityNotFoundExceptionHandler
	 */
	@Inject
	EntityNotFoundExceptionHandler enfeh;

	/**
	 * <p>
	 * Returns OK List<Ydelse> in JSON. Returns NotFound if no ydelser was
	 * found. Returns EJBException if there is problems with the Enterprise Java
	 * Beans eg. EntityManager
	 * </p>
	 * 
	 * @return Response OK 200
	 * @return Response Status NotFoundException
	 * @return Response Status EJBException (NotFoundException,
	 *         PersistenceException or status code 500)
	 * 
	 */
	@GET
	public Response getAll() {
		logger.finest("Get all ydelser.");

		List<Ydelse> ydelser = null;

		try {
			ydelser = ydelseService.findWithNamedQuery("Ydelse.findAll");
			return Response.ok(ydelser).build();
		} catch (EJBException ejbe) {

			// Check EJBException to be NotFoundException
			if (ejbe.getCausedByException().getClass().getSimpleName().equals(NotFoundException.class.getSimpleName()))

				// Return NotFoundExceptionHandler
				return nfeh.toResponse(ejbe);

			// Check EJBException to be PersistenceException
			else if (ejbe.getCausedByException().getClass().getSimpleName().equals(PersistenceException.class.getSimpleName()))

				// Return PersistenceExceptionHandler
				return peh.toResponse(ejbe);

			// Not specified Exceptions
			else

				// Return standard response object
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	/**
	 * 
	 * <p>
	 * Returns Response OK List<Ydelse> filtered by CPR. Returns
	 * NotFoundException if List<Ydelse> is empty.
	 * </p>
	 * 
	 * @param cpr
	 *            The CPR number
	 * @return Response OK 200 List<Ydelse> JSON.
	 * @return Response NotFoundException 404 if list is empty.
	 * 
	 */
	@GET
	@Path("/{cpr}")
	public Response getYdelserByCPR(@PathParam("cpr") String cpr) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cpr", cpr);

		logger.finest("Searching for CPR: " + cpr);

		try {

			List<Ydelse> ydelser = ydelseService.findWithNamedQuery("Ydelse.findByCPR", param);

			return Response.ok(ydelser).build();

		} catch (EJBException ejbe) {

			// NotFoundException
			if (ejbe.getCausedByException().getClass().getSimpleName().equals(NotFoundException.class.getSimpleName()))
				return nfeh.toResponse(ejbe);

			// PersistenceException
			else if (ejbe.getCausedByException().getClass().getSimpleName().equals(PersistenceException.class.getSimpleName()))
				return peh.toResponse(ejbe);

			// Status error 500
			else
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();

		}

	}

	/**
	 * <p>
	 * Return Response OK 200 List<Ydelse> filtered by CPR and Type. Return
	 * NotFoundException if List<Ydelse> if empty.
	 * </p>
	 * 
	 * @param type
	 *            Type of resource
	 * @param cpr
	 *            The CPR number
	 * 
	 * @return Response OK 200 List<Ydelse> if List<Ydelse> is NOT empty.
	 * @return Response NotFoundException 404 if List<Ydelse> is empty.
	 * @return Response PersistenceException 500
	 * 
	 */
	@GET
	@Path("/{type}/{cpr}")
	public Response getYdelserByTypeAndCPR(@PathParam("type") String type, @PathParam("cpr") String cpr) {

		Map<String, Object> param = new HashMap<String, Object>();

		param.put("type", type);
		param.put("cpr", cpr);

		logger.finest("Searching for type: " + type + " and CPR: " + cpr);

		try {
			List<Ydelse> ydelser = ydelseService.findWithNamedQuery("Ydelse.findByTypeAndCPR", param);

			return Response.ok(ydelser).build();
		} catch (EJBException ejbe) {

			// NotFoundException
			if (ejbe.getCausedByException().getClass().getSimpleName().equals(NotFoundException.class.getSimpleName())) {
				logger.warning("Not Found error");
				return nfeh.toResponse(ejbe);
			}
			// PersistenceException
			else if (ejbe.getCausedByException().getClass().getSimpleName().equals(PersistenceException.class.getSimpleName())) {
				logger.warning("Persistence error");
				return peh.toResponse(ejbe);
			}
			// Status code 500
			else {
				logger.warning("Internal Error 500");
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	/**
	 * 
	 * <p>
	 * Create a new ydelse. Return URI to the newly created ydelse. Return
	 * PersistenceException if persistence fails.
	 * </p>
	 * 
	 * @param cpr
	 *            The CPR number
	 * @param kr
	 *            The price
	 * @param dato
	 *            The date
	 * @param type
	 *            Type of resource
	 * @return Response object with URI to new ydelse.
	 * 
	 * @throw PersistenceException
	 */
	@POST
	public Response add(@FormParam("createCPR") String cpr, @FormParam("createKr") int kr, @FormParam("createDato") String dato,
			@FormParam("createType") String type) {

		Ydelse y = null;

		try {
			y = ydelseService.create(new Ydelse(null, cpr, kr, dato, type));
		} catch (EJBException ejbe) {

			// PersistenceException
			if (ejbe.getCausedByException().getClass().getSimpleName().equals(PersistenceException.class.getSimpleName())) {
				logger.warning("Persistence error");
				return peh.toResponse(ejbe);
			}
		}

		return Response.created(URI.create("ydelser/" + y.getId())).build();
	}

	/**
	 * <p>
	 * Return Response OK if entity was updated successfully. Return
	 * EntityNotFoundException if entity doesn't exist.
	 * </p>
	 * 
	 * @param id
	 *            Resource identificaiton
	 * @param cpr
	 *            The CPR number
	 * @param kr
	 *            The price
	 * @param dato
	 *            The date
	 * @param type
	 *            Resource type
	 * @return Response OK
	 * @return EntityNotFoundException if entity doesn't exist.
	 * @return PersistenceException if updating went wrong.
	 * @return Error code 500 if there is an internal error.
	 */
	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, @FormParam("cpr") String cpr, @FormParam("kr") int kr,
			@FormParam("dato") String dato, @FormParam("type") String type) {

		try {

			ydelseService.update(new Ydelse(id, cpr, kr, dato, type));

			return Response.ok().build();
		} catch (EJBException ejbe) {

			// EntityNotFoundException
			if (ejbe.getCausedByException().getClass().getSimpleName().equals(EntityNotFoundException.class.getSimpleName())) {
				logger.warning("Entity not found.");
				return enfeh.toResponse(ejbe);

				// PersistenceException
			} else if (ejbe.getCausedByException().getClass().getSimpleName().equals(PersistenceException.class.getSimpleName())) {
				logger.warning("Persistence error");
				return peh.toResponse(ejbe);

				// Internal error
			} else {
				logger.warning("Intenal error");
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	/**
	 * <p>
	 * Return Response OK if delete was successful. If id is invalid return
	 * EntityNotFoundException.
	 * </p>
	 * 
	 * @param id
	 *            Delete resource from id
	 * @return Response OK
	 * @return Response EntityNotFoundException
	 * @return Response PersistenceException
	 * @return Response Internal error 500
	 * 
	 */
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		logger.finest("Deleting ydelse with id: " + id);

		try {
			ydelseService.delete(Ydelse.class, id);

			return Response.ok().build();
		} catch (EJBException ejbe) {

			// EntityNotFoundException
			if (ejbe.getCausedByException().getClass().getSimpleName().equals(EntityNotFoundException.class.getSimpleName())) {
				logger.warning("Entity not found.");
				return enfeh.toResponse(ejbe);
			}
			// PersistenceExection
			else if (ejbe.getCausedByException().getClass().getSimpleName().equals(PersistenceException.class.getSimpleName())) {
				logger.warning("Persistence error");
				return peh.toResponse(ejbe);
			}
			// Internal error 500
			else {
				logger.warning("Internal error 500");
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}

		}
	}
}
