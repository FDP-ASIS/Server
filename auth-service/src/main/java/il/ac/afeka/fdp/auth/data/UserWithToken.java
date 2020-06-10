package il.ac.afeka.fdp.auth.data;

import il.ac.afeka.fdp.auth.data.boundary.UserBoundary;
import il.ac.afeka.fdp.auth.data.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWithToken {
    private UserBoundary user;
    private String token;

    public UserWithToken(UserEntity userEntity, String token) {
        this.user = new UserBoundary(userEntity);
        this.token = token;
    }
}
