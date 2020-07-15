package il.ac.afeka.fdp.user.data.boundary;

import lombok.Data;

@Data
public class PasswordBoundary {
    private String oldPassword;
    private String newPassword;
}
