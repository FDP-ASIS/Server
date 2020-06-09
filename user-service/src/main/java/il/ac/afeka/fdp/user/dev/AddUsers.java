package il.ac.afeka.fdp.user.dev;

import il.ac.afeka.fdp.user.data.Name;
import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.entity.UserEntity;
import il.ac.afeka.fdp.user.infra.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("dev")
public class AddUsers implements CommandLineRunner {

    @Autowired
    private UserService service;

    @Override
    public void run(String... args) throws Exception {
        service.signUp(Arrays.asList(
                UserEntity.of("123456789", "username1", new Name("first1", "middle1", "last2"), "user1@mail.com"),
                UserEntity.of("123456788", "username2", new Name("first1", "middle1", "last2"), "user2@mail.com")
                ), UserRoleEnum.STUDENT);
    }
}
