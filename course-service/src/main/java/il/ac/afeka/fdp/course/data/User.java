package il.ac.afeka.fdp.course.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Document("USERS")
public class User {
    @NonNull
    private String id;
    private String username;
    private String email;
}
