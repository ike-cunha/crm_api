package ikecrm.api.service;

import ikecrm.api.entity.PhotoEntity;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;

@Dependent
public class PhotoService {
    @Inject
    EntityManager em;

    public PhotoEntity findByID(String uuid) {
        var _uuid = UUID.fromString(uuid);
        try{
            var photo = em.find(PhotoEntity.class, _uuid);
            return photo;
        }catch (NullPointerException e){
            throw new NullPointerException();
        }
    }

    @Transactional
    public PhotoEntity merge(PhotoEntity photo) {
        return em.merge(photo);
    }
}
