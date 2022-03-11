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

    @Transient
    public String getPhotoUrl(){
        return "/photos/%s".formatted(uuid.toString());
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
}


