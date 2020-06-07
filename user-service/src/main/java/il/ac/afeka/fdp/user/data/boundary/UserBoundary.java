package il.ac.afeka.fdp.user.data.boundary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.entity.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBoundary {
    @ApiModelProperty(notes = "Real user id")
    private String id;
    private String username;
    private String email;
    @JsonIgnore
    private UserRoleEnum role;

    public UserBoundary(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole();
    }

    public UserEntity convertToEntity() {
        return UserEntity.of(id, username, email);
    }

    public UserEntity convertToEntity(String id) {
        return UserEntity.of(id, username, email);
    }
}
