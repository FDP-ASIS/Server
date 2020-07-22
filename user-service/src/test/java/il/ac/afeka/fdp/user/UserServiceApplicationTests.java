package il.ac.afeka.fdp.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import il.ac.afeka.fdp.user.data.Name;
import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.boundary.PasswordBoundary;
import il.ac.afeka.fdp.user.data.boundary.UserBoundary;
import il.ac.afeka.fdp.user.data.entity.UserEntity;
import il.ac.afeka.fdp.user.exception.user.UserNotFound;
import il.ac.afeka.fdp.user.infra.UserService;
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
import java.util.Base64;
import java.util.List;

import static de.flapdoodle.embed.process.runtime.AbstractProcess.TIMEOUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceApplicationTests {

	@Autowired
	private UserService userService;

	private int port;
	private String baseUrl;
	private RestTemplate restTemplate;
	private ObjectMapper jacksonMapper;


	/**
	 * Constructor
	 */
	public UserServiceApplicationTests(){

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
		this.baseUrl = "http://localhost:" + port + "/user";

		System.err.println(this.baseUrl);
		this.jacksonMapper = new ObjectMapper();
	}

	/**
	 * Logout user after finishing tests
	 */
	@AfterEach
	public void teardown() {
		this.userService.deleteAllUsers();
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
		assertThat(userService).isNotNull();
	}

	@Test
	public void getUser() throws JsonProcessingException {

		String email = "user6@mail.com";
		String id = "113112216";
		this.userService.signUp(Arrays.asList(
				UserEntity.of(id, "username10",
						new Name("first2", "last2"),
						email)
		), UserRoleEnum.STUDENT);

		UserBoundary response = this.restTemplate.getForObject(
				baseUrl + "/admin/{id}",
				UserBoundary.class,
				id
		);

		assertThat(jacksonMapper.writeValueAsString(response))
				.isNotNull();

		assertEquals(id,response.getId());
		assertEquals(email,response.getEmail());
	}

	@Test
	public void createANewUserSuccessfully() throws JsonProcessingException {
		String role= UserRoleEnum.valueOf("STUDENT").toString();
		UserBoundary user1= new UserBoundary();
		user1.setEmail("user1@gmail.com");
		user1.setUsername("user1");
		user1.setId("111111111");
		user1.setName(new Name("first1", "last2"));

		user1.setRole(UserRoleEnum.valueOf("STUDENT"));
		List<UserBoundary> myList = new ArrayList<>();
		myList.add(user1);

		List<Object> response= (List<Object>) this.restTemplate.postForObject(
				this.baseUrl+ "/admin/register/{role}",
				myList,
				Object.class,
				role
		);

		assertThat(jacksonMapper.writeValueAsString(response))
				.isNotNull();

		assertThat(this.userService.getUserById(user1.getId()))
				.isNotNull();

		assertEquals(user1.getId(),this.userService.getUserById(user1.getId()).getId());

		assertThat(this.userService.getUserById(user1.getId()).getName())
				.isEqualTo(user1.getName());
	}

	@Test
	public void updatePasswordSuccessfully() throws JsonProcessingException {

		String id = "111111115";
		String newPassword = "12345";

		this.userService.signUp(Arrays.asList(
				UserEntity.of(id, "username1",
						new Name("first1", "last2"),
						"user1@mail.com")
		), UserRoleEnum.STUDENT);

		PasswordBoundary pass1= new PasswordBoundary();
		pass1.setNewPassword(newPassword);
		pass1.setOldPassword("111111115");
		UserBoundary response= this.restTemplate.patchForObject(
				this.baseUrl+ "/{id}",
				pass1,
				UserBoundary.class,
				id
		);

		assertThat(jacksonMapper.writeValueAsString(response))
				.isNotNull();

		assertThat(this.userService.getUserById(id).getPassword())
				.isEqualTo(encryptPassword(newPassword));
	}

	@Test
	public void deleteUserSuccessfully() throws JsonProcessingException {

		String id = "888888888";
		this.userService.signUp(Arrays.asList(
				UserEntity.of(id, "username8",
						new Name("first8", "last8"),
						"user8@mail.com")
		), UserRoleEnum.STUDENT);

		this.restTemplate.delete(
				this.baseUrl+ "/admin/{id}",
				id
		);

		try {
			this.userService.getUserById(id);
		} catch (UserNotFound e) {
			assertEquals("User does not exists with id: " +id, e.getMessage());
		}
	}

	private String encryptPassword(String password) {
		// TODO change password encryption
		return Base64.getEncoder().encodeToString(password.getBytes());
	}
}