package il.ac.afeka.fdp.user.dev;

import il.ac.afeka.fdp.user.data.Name;
import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.entity.UserEntity;
import il.ac.afeka.fdp.user.infra.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Profile("dev")
public class AddUsers implements CommandLineRunner {

    @Autowired
    private UserService service;

    @Override
    public void run(String... args) throws Exception {
        try {
            service.signUp(Collections.singletonList(
                    UserEntity.of("123456789",
                            "Student",
                            new Name("Israel", "Israeli"),
                            "student@gmail.com")
            ), UserRoleEnum.STUDENT);

            service.signUp(Collections.singletonList(
                    UserEntity.of("111111111",
                            "Lecturer",
                            new Name("LecturerF", "LecturerL"),
                            "lecturer@gmail.com")
            ), UserRoleEnum.LECTURER);

            service.signUp(Collections.singletonList(
                    UserEntity.of("222222222",
                            "Admin",
                            new Name("AdminF", "AdminL"),
                            "admin@gmail.com")
            ), UserRoleEnum.ADMIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
