package ikecrm.api.service;

import ikecrm.api.entity.UserEntity;
import io.quarkus.security.User;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.keycloak.authorization.client.AuthzClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Dependent
public class UserService {
    static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    JsonWebToken jwt;

    @Inject
    EntityManager em;

    //@Inject
    //AuthzClient authz;

    public UserEntity getCurrentUser(){
        var principal =  securityIdentity.getPrincipal();
        var subject = jwt.getSubject();
        var username = principal.getName();
        log.info("Current username: {} subject: {}" , username, subject);
        var jpql = "select u from User u where u.username = :username";
        var user  = (UserEntity) null;
        try {
            user = em.createQuery(jpql, UserEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }catch (NoResultException ex){
            user = new UserEntity(username);
            user = em.merge(user);
        }
        return user;
    }

    public void deleteUser(){
    }
}
