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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/user/customers")
@RolesAllowed({"user", "admin"})
public class CustomerResource {
    @Inject
    CustomerService customerService;

    @Inject
    UserService userService;

    @GET
    @Produces(APPLICATION_JSON)
    public List<CustomerEntity> getAll(){
        return customerService.findAll();
    }

    @GET
    @Path("{uuid}")
    @Produces(APPLICATION_JSON)
    public CustomerEntity getById(@PathParam("uuid") String uuid) throws Exception {
        var id = UUID.fromString(uuid);
        try {
            return customerService.findById(id);
        } catch (Exception e) {
            throw new Exception(e.getCause());
        }
    }

    @Transactional
    @POST
    @Produces(APPLICATION_JSON)
    public CustomerEntity post(@QueryParam("name") @DefaultValue("Jane") String name,
                               @QueryParam("surname") @DefaultValue("foobar") String surname ){
        var c = new CustomerEntity();
        c.setName(name);
        c.setSurname(surname);
        customerService.persist(c);
        return c;
    }

    @Transactional
    @DELETE
    @Path("{uuid}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response delete(@PathParam("uuid") String uuid) {
        try {
            var id = UUID.fromString(uuid);
            var deleted = customerService.deleteById(id);
            if(deleted) {
                return Response.ok("Customer Deleted").build();
            }
            return Response.serverError().build();
        }catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

    @Transactional
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(APPLICATION_JSON)
    public Response put(CustomerEntity customer){
        try{
            var updated = customerService.merge(customer);
            var msg ="Customer updated %s".formatted(updated.getUuid());
            return Response.ok(msg).build();
        }catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
}
