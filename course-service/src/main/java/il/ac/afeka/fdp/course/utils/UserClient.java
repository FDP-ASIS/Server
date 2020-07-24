package il.ac.afeka.fdp.course.utils;

import il.ac.afeka.fdp.course.data.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service/user/admin")
public interface UserClient {
    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    User getUser(@PathVariable("id") String id);
}
