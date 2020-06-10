package il.ac.afeka.fdp.user.data.boundary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import il.ac.afeka.fdp.user.data.Name;
import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.entity.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBoundary {
    @ApiModelProperty(notes = "Real user id")
    private String id;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private Name name;
    @JsonIgnore
    @NotBlank
    private UserRoleEnum role;

    public UserBoundary(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole();
        try {
            this.name = (Name) userEntity.getName().clone();
        } catch (CloneNotSupportedException e) {
            this.name = null;
            e.printStackTrace();
        }
    }

    public UserEntity convertToEntity() {
        return UserEntity.of(id, username,name, email);
    }

    public UserEntity convertToEntity(String id) {
        return UserEntity.of(id, username,name, email);
    }
}
