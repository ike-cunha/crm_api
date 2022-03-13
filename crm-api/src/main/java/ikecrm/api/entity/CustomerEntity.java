package ikecrm.api.entity;

//JPA
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;

import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Type;


@Entity(name = "Customer")
@Table(name = "customer")
@NamedQueries({
        @NamedQuery(name = "customer.all", query="select c from Customer c")
})
public class CustomerEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    UUID uuid;
    
    @NotNull
    String name;
    
    @NotNull
    String surname;

    @ManyToOne
    UserEntity createdBy;

    @ManyToOne
    UserEntity updatedBy;

    @OneToOne
    @JsonIgnore
    PhotoEntity photo;

    public CustomerEntity(){}

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public PhotoEntity getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoEntity photo) {
        this.photo = photo;
    }
    static final String DEFAULT_PHOTO = "https://www.meme-arsenal.com/memes/591dbcd3275641926626f0eb733c4311.jpg";

    @Transient
    public String getPhotoUrl(){
        if (photo==null) return DEFAULT_PHOTO;
        return "/photos/%s".formatted(photo.uuid.toString());
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public UserEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(UserEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void merge(CustomerEntity customer) {
        setName(customer.getName());
        setSurname(customer.getSurname());
    }
}


