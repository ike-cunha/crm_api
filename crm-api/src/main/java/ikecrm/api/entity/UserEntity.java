package ikecrm.api.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "Users")
@Table(name = "users")
public class UserEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    UUID uuid;

    String username;

    @ManyToOne
    UserEntity createdBy;

    public UserEntity(){}

    public UserEntity(String username) {
        this.username = username;
    }

    public UserEntity(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }
}
