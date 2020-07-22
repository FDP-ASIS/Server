package il.ac.afeka.fdp.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import il.ac.afeka.fdp.auth.data.Name;
import il.ac.afeka.fdp.auth.data.UserWithToken;
import il.ac.afeka.fdp.auth.data.boundary.UserBoundary;
import il.ac.afeka.fdp.auth.data.boundary.UsernamePasswordBoundary;
import il.ac.afeka.fdp.auth.infra.AuthService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

@Component
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AuthServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Authentication tests")
class AuthServiceApplicationTests {

	@Autowired
	private AuthService authService;

	private int port;
	private String baseUrl;
	private RestTemplate restTemplate;
	private ObjectMapper jacksonMapper;

	/**
	 * Constructor
	 */
	public AuthServiceApplicationTests(){

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
		this.baseUrl = "http://localhost:" + port + "/auth";

		System.err.println(this.baseUrl);
		this.jacksonMapper = new ObjectMapper();
	}

	/**
	 * Logout user after finishing tests
	 */

	@AfterEach
	public void teardown()
	{
		this.authService.logout();
	}

	/**
	 * Given nothing
	 * When nothing
	 * Then the server is loading properly
	 */
	@Test
	public void testServerInitializesProperly() {
	}

	/**
	 * Check is authService exists
	 */
	@Test
	public void contextLoads() {
		assertThat(authService).isNotNull();
	}

	/**
	 * User login successfully to the system
	 */
	//@Test
	public void getARegisteredUserFromTheServerSuccessfully() {
		String role="STUDENT";
		UserBoundary user1= new UserBoundary();
		user1.setEmail("user1@gmail.com");
		user1.setUsername("user1");
		user1.setId("111111111");
		user1.setName(new Name("first1", "last2"));

		/*user1.setRole(UserRoleEnum.valueOf("STUDENT"));
		List<UserBoundary> myList = new ArrayList<>();
		myList.add(user1);

		this.restTemplate.postForObject(
				this.baseUrl+ "/admin/register/{role}",
				myList,
				Object.class,
				role
		);*/

		UsernamePasswordBoundary user= new UsernamePasswordBoundary();
		user.setUsername(user1.getUsername());
		user.setPassword(user1.convertToEntity().getPassword());
		UserWithToken response= this.restTemplate.postForObject(
				this.baseUrl+ "/login",
				user,
				UserWithToken.class
		);


		assertThat(response)
				.isNotNull();
	}
}
