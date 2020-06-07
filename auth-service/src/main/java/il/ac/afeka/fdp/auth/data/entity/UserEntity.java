package il.ac.afeka.fdp.auth.data.entity;

import il.ac.afeka.fdp.auth.data.UserRoleEnum;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

@Data
@Document(collection = "USERS")
@RequiredArgsConstructor(staticName = "of")
@Validated
public class UserEntity {
    @Id
    private final String id;
    @NonNull
    private String username;
    private String password;
    @NonNull
    private String email;
    private UserRoleEnum role;
}