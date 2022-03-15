package ikecrm.api.service;

import ikecrm.api.entity.CustomerEntity;
import ikecrm.api.entity.UserEntity;
import ikecrm.api.utils.UserAlreadyExistsException;
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

    @Inject KeycloakService kcs;

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

    public List<UserEntity> findAll(){
        var result = em.createNamedQuery("user.all").getResultList();
        return  result;
    }

    public UserEntity findById(UUID id){
        return UserEntity.findById(id);
    }

    public boolean alreadyExists(String username) throws NoResultException {
        Object result;
        try{
            result = em.createNamedQuery("user.username").setParameter("username", username).getSingleResult();
        }catch (NoResultException e){
            return false;
        }
        return result != null;
    }

    public boolean create(UserEntity user){
        if (alreadyExists(user.getUsername())) {
            throw new UserAlreadyExistsException();
        }
        var currentUser = getCurrentUser();
        user.setCreatedBy(currentUser);
        UserEntity.persist(user);
        kcs.create(user);
        return true;
    }

    public UserEntity merge(UserEntity user) {
        var result = em.find(UserEntity.class, user.getUuid());
        if (result == null){
            log.warn("Non-existing user passed to merge(), used persist() instead");
            em.persist(user);
            result = user;
        } else{
            log.info("Found customer with UUID {}, merging", user.getUuid());
            user.setCreatedBy(result.getCreatedBy());
            user.setUsername(result.getUsername());
            result.merge(user);
            em.merge(user);
        }
        return result;
    }

    public boolean deleteById(UUID id) throws Exception{
        try {
            var resp = UserEntity.deleteById(id);
            if (!resp) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
