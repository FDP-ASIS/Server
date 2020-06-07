package il.ac.afeka.fdp.user.data.entity;

import il.ac.afeka.fdp.user.data.UserRoleEnum;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Document(collection = "USERS")
@RequiredArgsConstructor(staticName = "of")
@Validated
public class UserEntity {
    @Id
    @Size(min = 9, max = 9)
    private final String id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @Indexed(unique = true)
    @NonNull
    @Email
    private String email;
    private String password;
    private UserRoleEnum role;
}