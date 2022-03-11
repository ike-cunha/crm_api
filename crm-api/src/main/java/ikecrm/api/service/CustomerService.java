package ikecrm.api.service;

import java.util.List;
import java.util.UUID;

import ikecrm.api.entity.*;
import io.quarkus.security.identity.SecurityIdentity;

import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Dependent
public class CustomerService {
    @Inject
    UserService userService;

    @Inject
    EntityManager em;

    public List<CustomerEntity> findAll(){
        var jpql = "select c from Customer c";
        var result = em.createQuery(jpql).getResultList();
        return  result;
    }

    public CustomerEntity findById(UUID id){
        // TODO: Needs try catch?
        return CustomerEntity.findById(id);
    }

    public boolean create(CustomerEntity customer){
        var user = userService.getCurrentUser();
        try {
            // TODO: UUID ==> The customer should have a reference to the user who created it.
            //customer.setCreatedBy(securityIdentity.getPrincipal().getName());
            customer.setCreatedBy(user);
            customer.setUpdatedBy(user);
            CustomerEntity.persist(customer);
        } catch (Exception e) {
            return false;
        }
        return true;
    }    

    @Transactional
    public boolean deleteById(CustomerEntity customer){
        try {
            CustomerEntity.deleteById(customer);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}