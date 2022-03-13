package ikecrm.api.service;

import ikecrm.api.entity.CustomerEntity;
import ikecrm.api.entity.UserEntity;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

@Dependent
public class UserService {
    static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    JsonWebToken jwt;

    @Inject
    EntityManager em;

    public UserEntity getCurrentUser(){
        var principal =  securityIdentity.getPrincipal();
        var subject = jwt.getSubject();
        var username = principal.getName();
        log.info("Current username: {} subject: {}" , username, subject);
        var jpql = "select u from Users u where u.username = :username";
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

    public List<UserEntity> list(){
        var jpql = "select u from Users u";
        var result = em.createQuery(jpql).getResultList();
        return  result;
    }

    public UserEntity find(UUID id){
        return UserEntity.findById(id);
    }

    public boolean create(UserEntity user){
        var loggedUser = getCurrentUser();
        try {
            user.setCreatedBy(loggedUser);
            UserEntity.persist(user);
            //CRIAR NO KEYCLOAK
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void delete(UUID id){
        //CRIAR NO KEYCLOAK
    }

    public void update(){
        //CRIAR NO KEYCLOAK
    }
}
