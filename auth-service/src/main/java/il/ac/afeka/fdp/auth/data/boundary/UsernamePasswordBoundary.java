package il.ac.afeka.fdp.auth.data.boundary;

import lombok.Data;

@Data
public class UsernamePasswordBoundary {
    private String username;
    private String password;
}
