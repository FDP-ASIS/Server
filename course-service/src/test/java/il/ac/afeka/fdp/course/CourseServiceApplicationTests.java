package il.ac.afeka.fdp.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import il.ac.afeka.fdp.course.data.boundary.CourseBoundary;
import il.ac.afeka.fdp.course.data.boundary.IdBoundary;
import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import il.ac.afeka.fdp.course.exceptions.course.CourseNotFoundException;
import il.ac.afeka.fdp.course.infra.CourseService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.flapdoodle.embed.process.runtime.AbstractProcess.TIMEOUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CourseServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseServiceApplicationTests {

	@Autowired
	private CourseService courseService;

	private int port;
	private String baseUrl;
	private RestTemplate restTemplate;
	private ObjectMapper jacksonMapper;


	/**
	 * Constructor
	 */
	public CourseServiceApplicationTests(){

	}

	/**
	 * Initialize port
	 */
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Initialize for all the tests
	 */
	@BeforeAll
	@PostConstruct
	public void init(){
		this.restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(TIMEOUT);
        requestFactory.setReadTimeout(TIMEOUT);
        restTemplate.setRequestFactory(requestFactory);
		this.baseUrl = "http://localhost:" + port + "/course";

		System.err.println(this.baseUrl);
		this.jacksonMapper = new ObjectMapper();
	}

	/**
	 * Delete all courses from the database after finishing all the tests
	 */
	@AfterEach
	public void teardown()
	{
		this.courseService.deleteAll();
	}

	/**
	 * Given nothing
	 * When nothing
	 * Then the server is loading properly
	 */
	@Test
	public void testServerInitializesProperly() {
	}

	@Test
	public void contextLoads() {
		assertThat(courseService).isNotNull();
	}

	@Test
	public void addCourse() throws JsonProcessingException {

	    CourseBoundary course1= new CourseBoundary();
	    course1.setCode(12349L);
	    course1.setName("course2");

		List<CourseBoundary> myList= new ArrayList<>();
        myList.add(course1);

        List<Object> response= (List<Object>) this.restTemplate.postForObject(
                this.baseUrl+ "/admin",
                myList,
                Object.class
        );

		assertThat(jacksonMapper.writeValueAsString(response))
				.isNotNull();

        assertThat(this.courseService.getCourseByCode(12349L))
                .isNotNull();

        assertThat(this.courseService.getCourseByCode(course1.getCode()).getName())
                .isEqualTo(course1.getName());
	}

    @Test
    public void deleteCourseSuccessfully() throws JsonProcessingException {

        Long code = 12349L;
        this.courseService.create(Arrays.asList(
                CourseEntity.of(code, "course3")
        ));

        this.restTemplate.delete(
                this.baseUrl+ "/admin/{code}",
                code
        );

        try {
            this.courseService.getCourseByCode(code);
        } catch (CourseNotFoundException e) {
            assertEquals("Course does not exists with code: " + code, e.getMessage());
        }
    }

    //@Test
    public void addSoftwareToCourseSuccessfully() throws JsonProcessingException {
        Long code = 12350L;
        this.courseService.create(Arrays.asList(
                CourseEntity.of(code, "course4")
        ));

        IdBoundary id= new IdBoundary();
        id.setId("123");
        CourseBoundary response= this.restTemplate.patchForObject(
                this.baseUrl+ "/lecturer/{code}/{id}",
                id,
                CourseBoundary.class,
                code,
                id
        );

        assertThat(jacksonMapper.writeValueAsString(response))
                .isNotNull();

        assertThat(this.courseService.getCourseByCode(code).getSoftware().stream().map(
                course->course.getId()))
                .isEqualTo(id);
    }

	//@Test
	public void addStudentToCourseSuccessfully() throws JsonProcessingException {
		Long code = 12356L;
		this.courseService.create(Arrays.asList(
				CourseEntity.of(code, "course6")
		));

		String id= "111111111";
		Object response= this.restTemplate.patchForObject(
				this.baseUrl+ "/{code}?type={id}",
				"",
				Object.class,
				code,
				id
		);

		assertThat(jacksonMapper.writeValueAsString(response))
				.isNotNull();

		assertThat(this.courseService.getCourseByCode(code).getStudents()
				.stream()
				.map(
				student -> student.getId()))
				.isEqualTo(id);
	}

	//@Test
	public void addLecturerToCourseSuccessfully() throws JsonProcessingException {
		Long code = 12213L;
		this.courseService.create(Arrays.asList(
				CourseEntity.of(code, "course8")
		));

		String id= "222222222";
		Object response= this.restTemplate.patchForObject(
				this.baseUrl+ "/admin/{code}/{id}",
				"",
				Object.class,
				code,
				id
		);

		assertThat(jacksonMapper.writeValueAsString(response))
				.isNotNull();

		assertThat(this.courseService.getCourseByCode(code).getLecturers()
				.stream()
				.map(lecturer -> lecturer.getId()))
				.isEqualTo(id);
	}
}