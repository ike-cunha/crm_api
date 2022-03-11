package ikecrm.api.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "Users")
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    UUID uuid;

    String username;

    public UserEntity(){}

    public UserEntity(String username) {
        this.username = username;
    }

    public UserEntity(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }
}
