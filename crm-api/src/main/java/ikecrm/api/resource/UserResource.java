package ikecrm.api.resource;

import ikecrm.api.entity.CustomerEntity;
import ikecrm.api.entity.UserEntity;
import ikecrm.api.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@RolesAllowed("admin")
@Path("user/user")
//TODO: Add keycloack integration
public class UserResource {
    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> list()
    {
        return userService.list();
    }

    @GET
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity find(@PathParam("uuid") String uuid) throws Exception {
        var id = UUID.fromString(uuid);
        try {
            return userService.find(id);
        } catch (Exception e) {
            throw new Exception(e.getCause());
        }
    }

    @Transactional
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(UserEntity customer){
        try {
            if (userService.create(customer)) {
                return Response.ok("User Created").build();
            }
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

    @Transactional
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response delete(String uuid){
        try{
            var id = UUID.fromString(uuid);
            userService.delete(id);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.ok("User Deleted").build();
    }

    // Update users.
    public UserEntity update(){
        return null;
    }

    //TODO: Change admin status.
    public void changeStatus(){}
}
