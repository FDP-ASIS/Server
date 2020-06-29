package il.ac.afeka.fdp.software.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Global {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
