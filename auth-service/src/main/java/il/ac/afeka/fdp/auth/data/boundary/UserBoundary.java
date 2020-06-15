package il.ac.afeka.fdp.auth.data.boundary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import il.ac.afeka.fdp.auth.data.Name;
import il.ac.afeka.fdp.auth.data.UserRoleEnum;

import il.ac.afeka.fdp.auth.data.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBoundary {
    private String id;
    private String username;
    private String email;
    private Name name;
    private UserRoleEnum role;

    public UserBoundary(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.name = userEntity.getName();
        this.role = userEntity.getRole();
    }

    public UserEntity convertToEntity() {
        return UserEntity.of(id, username, email);
    }
}
