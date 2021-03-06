package il.ac.afeka.fdp.user.data.entity;

import il.ac.afeka.fdp.user.config.validation.ValidNumber;
import il.ac.afeka.fdp.user.data.Name;
import il.ac.afeka.fdp.user.data.UserRoleEnum;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Document(collection = "USERS")
@RequiredArgsConstructor(staticName = "of")
@Validated
public class UserEntity {
    @Id
    @Size(min = 9, max = 9)
    @ValidNumber
    private final String id;
    @Indexed(unique = true)
    @NonNull
    @Size(min = 5, max = 20)
    private String username;
    @NonNull
    private Name name;
    @Indexed(unique = true)
    @NonNull
    @Email
    private String email;
    @Size(min = 5, max = 15)
    private String password;
    private UserRoleEnum role;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
}