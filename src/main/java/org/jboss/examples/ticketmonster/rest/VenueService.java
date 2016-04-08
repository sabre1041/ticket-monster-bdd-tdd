package org.jboss.examples.ticketmonster.rest;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.examples.ticketmonster.model.Venue;

/**
 * <p>
 *     A JAX-RS endpoint for handling {@link Venue}s. Inherits the actual
 *     methods from {@link BaseEntityService}.
 * </p>
 *
 * @author Marius Bogoevici
 */
@Path("/venues")
/**
 * <p>
 *     This is a stateless service, we declare it as an EJB for transaction demarcation
 * </p>
 */
@Stateless
public class VenueService extends BaseEntityService<Venue> {

    public VenueService() {
        super(Venue.class);
    }

    @GET
    @Path("/city/{city}")
    @Produces("application/json")
    public Response findByCityName(@PathParam("city") String name)
    {
       TypedQuery<Venue> findByIdQuery = getEntityManager().createQuery("SELECT DISTINCT v FROM Venue v where UPPER(v.address.city) = :city", Venue.class);
       findByIdQuery.setParameter("city", name.toUpperCase());
       Venue entity;
       try
       {
          entity = findByIdQuery.getSingleResult();
       }
       catch (NoResultException nre)
       {
          entity = null;
       }
       if (entity == null)
       {
          return Response.status(Status.NOT_FOUND).build();
       }
       return Response.ok(entity).build();
    }

}