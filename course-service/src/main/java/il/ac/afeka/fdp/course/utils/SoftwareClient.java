package il.ac.afeka.fdp.course.utils;

import il.ac.afeka.fdp.course.data.Software;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "software-service/software")
public interface SoftwareClient {

    @GetMapping(value = "id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Software getSoftware(@PathVariable("id") String id);
}
