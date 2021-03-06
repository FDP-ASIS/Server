package il.ac.afeka.fdp.user.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserRoleEnum {
    ADMIN("admin"), LECTURER("lecturer"), STUDENT("student");

    @Getter
    private final String name;
}
