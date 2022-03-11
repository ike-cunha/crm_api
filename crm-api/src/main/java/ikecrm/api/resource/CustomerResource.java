package ikecrm.api.resource;

//JAX-RS
import javax.annotation.security.RolesAllowed;
import javax.inject.*;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import ikecrm.api.entity.*;
import java.util.*;
import ikecrm.api.service.*;

@Path("/user/customers")
public class CustomerResource {
    @Inject
    CustomerService customerService;

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerEntity> findAll(){
        return customerService.findAll();
    }

    @GET
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerEntity findById(@PathParam("uuid") String uuid) throws Exception {
        var id = UUID.fromString(uuid);
        try {
            return customerService.findById(id);
        } catch (Exception e) {
            throw new Exception(e.getCause());
        }
    }

    @Transactional
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(CustomerEntity customer){
        try {
            if (customerService.create(customer)) {
                return Response.ok("Customer Created").build();
            }
            return Response.status(Status.SERVICE_UNAVAILABLE).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

}
