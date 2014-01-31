package dk.convergens.ydelse.exception;

import javax.ejb.EJBException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionHandler implements ExceptionMapper<EJBException> {

	@Override
	public Response toResponse(EJBException ex) {
		return Response.status(Status.NOT_FOUND).entity(ex.getMessage()).build();
	}
	
}
