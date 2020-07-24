package il.ac.afeka.fdp.software.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Data
@Document(collection = "SOFTWARE")
@NoArgsConstructor
public class Software {
    @MongoId(targetType = FieldType.OBJECT_ID)
    private String id;
    @Indexed(unique=true)
    private String name;
    @Indexed(unique=true)
    private String version;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;

    public Software(String name, String version) {
        this.name = name;
        this.version = version;
    }
}