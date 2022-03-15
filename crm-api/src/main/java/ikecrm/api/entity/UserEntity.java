package ikecrm.api.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "Users")
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "user.all", query="select u from Users u"),
        @NamedQuery(name = "user.username", query = "select u.uuid from Users u where u.username = :username")
})
public class UserEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    UUID uuid;

    String username;
    String name;
    String surname;
    boolean admin;

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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean getAdmin() {
        return admin;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void merge(UserEntity user) {
        setName(user.getName());
        setSurname(user.getSurname());
        setAdmin(user.getAdmin());
    }
}
