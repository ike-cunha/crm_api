package ikecrm.api.service;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import java.util.List;

@Dependent
public class KeycloakService {
    private static final Logger log = LoggerFactory.getLogger(KeycloakService.class);

    @ConfigProperty(name = "crm.keycloak.url")
    String serverUrl;
    @ConfigProperty(name = "crm.keycloak.adminRealm")
    String adminRealmName;
    @ConfigProperty(name = "crm.keycloak.realm")
    String crmRealmName;
    @ConfigProperty(name = "crm.keycloak.username")
    String username;
    @ConfigProperty(name = "crm.keycloak.password")
    String password;
    @ConfigProperty(name = "crm.keycloak.clientId")
    String clientId;
    @ConfigProperty(name = "crm.keycloak.clientSecret")
    String clientSecret;

    public void delete(String username){
        var uuid = searchUserIdByName(username);
        users().delete(uuid);
        log.info("delete done");
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
