package ikecrm.api.resource;

import ikecrm.api.entity.UserEntity;
import ikecrm.api.service.UserService;
import ikecrm.api.utils.UserAlreadyExistsException;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RolesAllowed("admin")
@Path("user/user")
//TODO: Add keycloack integration
public class UserResource {
    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> getAll()
    {
        return userService.findAll();
    }

    @GET
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity getById(@PathParam("uuid") String uuid) throws Exception {
        var id = UUID.fromString(uuid);
        try {
            return userService.findById(id);
        } catch (Exception e) {
            throw new Exception(e.getCause());
        }
    }

    @Transactional
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(UserEntity customer){
        try {
            userService.create(customer);
        } catch (UserAlreadyExistsException ex){
            throw new WebApplicationException("Username already exists", 400);
        } catch (Exception e) {
            throw new WebApplicationException("Unknown error saving customer", 500);
        }
    }

    @Transactional
    @DELETE
    @Path("{uuid}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response delete(@PathParam("uuid") String uuid) {
        try {
            var id = UUID.fromString(uuid);
            var deleted = userService.deleteById(id);
            if(deleted) {
                return Response.ok("User Deleted").build();
            }
            return Response.serverError().build();
        }catch (Exception e){
            return Response.serverError().entity(e).build();
        }
    }

    @Transactional
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(APPLICATION_JSON)
    public Response put(UserEntity user){
        try{
            var updated = userService.merge(user);
            var msg ="User updated %s".formatted(updated.getUuid());
            return Response.ok(msg).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

    //TODO: Change admin status.
    public void changeStatus(){}
}
