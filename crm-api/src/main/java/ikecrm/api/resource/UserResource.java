package ikecrm.api.resource;

import ikecrm.api.entity.UserEntity;
import ikecrm.api.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Path;
import java.util.List;
import java.util.UUID;

@RolesAllowed("admin")
@Path("user/user")
//TODO: Add keycloack integration
public class UserResource {
    @Inject
    UserService userService;

    // Create users
    public UserEntity create(String username){
        return null;
    }

    // Delete users.
    public void delete(){

    }

    // Update users.
    public UserEntity update(){
        return null;
    }

    // List users.
    public List<UserEntity> listUsers(){
        return List.of();
    }

    //TODO: Change admin status.
    public void changeStatus(){}
}
