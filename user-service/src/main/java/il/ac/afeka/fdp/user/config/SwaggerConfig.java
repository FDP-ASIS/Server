package il.ac.afeka.fdp.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger.meta")
@Data
public class SwaggerConfig {

    @Value("${spring.application.name:User-Service}")
    private String appName;
    private String description;
    private Contact contact;
    private License license;
    private String version;

    @Data
    private static class License{
        private String name;
        private String url;
    }

    @Data
    private static class Contact{
        private String name;
        private String email;
        private String url;

        public springfox.documentation.service.Contact getContact(){
            return new springfox.documentation.service.Contact(name,url,email);
        }
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("il.ac.afeka.fdp"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder()
                .title(appName)
                .description(description)
                .contact(contact.getContact())
                .license(license.name)
                .licenseUrl(license.url)
                .version(version)
                .build();
    }
}
