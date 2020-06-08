package il.ac.afeka.fdp.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
public class CourseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseServiceApplication.class, args);
	}

}

//    SOFTWARE_ENGINEER("SOFTWARE_ENGINEER",1),
//    INDUSTRIAL_ENGINEERING_MANAGEMENT("INDUSTRIAL_ENGINEERING_MANAGEMENT",2),
//    ELECTRICAL_ENGINEER("ELECTRICAL_ENGINEER",3);