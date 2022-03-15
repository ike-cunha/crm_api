package ikecrm.api.service;

import ikecrm.api.entity.UserEntity;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.ws.rs.core.Response;
import java.util.List;

@Dependent
public class KeycloakService {
    private static final Logger log = LoggerFactory.getLogger(KeycloakService.class);

    @ConfigProperty(name = "crm.keycloak.url")
    String serverUrl;
    @ConfigProperty(name = "crm.keycloak.adminRealmName")
    String adminRealmName;
    @ConfigProperty(name = "crm.keycloak.realmName")
    String crmRealmName;
    @ConfigProperty(name = "crm.keycloak.username")
    String username;
    @ConfigProperty(name = "crm.keycloak.password")
    String password;
    @ConfigProperty(name = "crm.keycloak.clientId")
    String clientId;
    @ConfigProperty(name = "crm.keycloak.clientSecret")
    String clientSecret;

    public void create(UserEntity user) {
        try {
            var resp = users().create(userRepresentation(user));
            if (resp.getStatus() != Response.Status.OK.getStatusCode()) {
                log.warn("Create was not possible: user => {} {}", user.getName(), user.getSurname());
            }else
                log.info("Create completed: user => {} {}", user.getName(), user.getSurname());
        }catch(Exception e){
            log.error("Something bad", e.getMessage());
            //e.printStackTrace();
        }
    }

    public void delete(String username){
        var uuid = searchUserIdByName(username);
        var resp = users().delete(uuid);
        if (resp.getStatus() != Response.Status.OK.getStatusCode()) {
            log.warn("Delete was not possible: uuid {}", uuid);
        }
        log.info("Delete completed: uuid {}", uuid);
    }

    private UserRepresentation userRepresentation(UserEntity user) {
        UserRepresentation userRepre = new UserRepresentation();
        userRepre.setUsername(user.getUsername().toLowerCase());
        userRepre.setFirstName(user.getUsername());
        userRepre.setLastName(user.getSurname());
        userRepre.setRealmRoles(realmRoles(user.getAdmin()));
        var creds = credentials("Masterkey123");
        userRepre.setCredentials(creds);
        userRepre.setEnabled(true);
        return userRepre;
    }

    private List<String> realmRoles(boolean isAdmin){
        List<String> roles = List.of("user");
        if (isAdmin) {
            roles.add("admin");
        }
        return roles;


    }

    private List<CredentialRepresentation> credentials (String password) {
        CredentialRepresentation credentialRepre = new CredentialRepresentation();
        credentialRepre.setType("password");
        credentialRepre.setValue(password);
        List<CredentialRepresentation> credentialsList = List.of(credentialRepre);
        return credentialsList;
    }

    private String searchUserIdByName(String username) {
        var first = 0;
        var max = 1;
        var search = users().search(username, first, max);
        if (search.isEmpty()) throw new RuntimeException("User not found");
        var user = search.get(0);
        var id = user.getId();
        log.info("username {} => id {}", username, id);
        return id;
    }

    private UsersResource users(){
        var users = realm().users();
        return users;
    }

    private RealmResource realm(){
        var kc = keycloak();
        var realm = kc.realm(crmRealmName);
        return realm;
    }

    private Keycloak keycloak() {
            var kc = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(adminRealmName)
                    .username(username)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();
            return kc;
    }
}
