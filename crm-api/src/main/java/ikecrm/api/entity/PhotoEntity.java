package ikecrm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "Photo")
@Table(name = "photo")
public class PhotoEntity {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    UUID uuid;

    @Column(columnDefinition = "LONGTEXT")
    String content;

    String mimeType;

    public PhotoEntity(){}

    public PhotoEntity(String uuid, String content, String mimeType) {
        this.uuid = UUID.fromString(uuid);
        this.content = content;
        this.mimeType = mimeType;
    }

    public String getContent() {
        return content;
    }

    public String getMimeType() {
        return mimeType;
    }
}
