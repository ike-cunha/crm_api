package ikecrm.api.service;

import java.util.List;
import java.util.UUID;

import ikecrm.api.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Dependent
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    @Inject
    UserService userService;

    @Inject
    EntityManager em;

    public List<CustomerEntity> findAll(){
        var result = em.createNamedQuery("customer.all").getResultList();
        return  result;
    }

    public CustomerEntity findById(UUID id){
        return CustomerEntity.findById(id);
    }


    public CustomerEntity merge(CustomerEntity customer) {
        var result = em.find(CustomerEntity.class, customer.getUuid());
        if (result == null){
            log.warn("Non-existing customer passed to merge(), use persist() instead");
            em.persist(customer);
            result = customer;
        } else{
            log.info("Found customer with UUID {}, merging", customer.getUuid());
            customer.setCreatedBy(result.getCreatedBy());
            result.merge(customer);
            em.merge(customer);
        }
        var user = userService.getCurrentUser();
        result.setUpdatedBy(user);
        return result;
    }

    public CustomerEntity persist(CustomerEntity customer) {
        var user = userService.getCurrentUser();
        customer.setCreatedBy(user);
        customer.setUpdatedBy(user);
        CustomerEntity.persist(customer);
        return customer;
    }



    public boolean deleteById(UUID id) throws Exception{
        try {
            var resp = CustomerEntity.deleteById(id);
            if (!resp) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}