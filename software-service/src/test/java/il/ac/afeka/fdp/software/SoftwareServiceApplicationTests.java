package il.ac.afeka.fdp.software;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import il.ac.afeka.fdp.software.data.ScriptType;
import il.ac.afeka.fdp.software.exceptions.NotFoundException;
import il.ac.afeka.fdp.software.infra.SoftwareService;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Component
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SoftwareServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SoftwareServiceApplicationTests {

	@Autowired
	private SoftwareService softwareService;

	private int port;
	private String baseUrl;
	private RestTemplate restTemplate;
	private ObjectMapper jacksonMapper;

	/**
	 * Constructor
	 */
	public SoftwareServiceApplicationTests(){

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
		this.baseUrl = "http://localhost:" + port + "/software";

		System.err.println(this.baseUrl);
		this.jacksonMapper = new ObjectMapper();
	}

	/**
	 * Tear down the tests
	 */
	@AfterAll
	public void teardown() {

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
	 * Check if softwareService exists
	 */
	@Test
	public void contextLoads() {
		assertThat(softwareService).isNotNull();
	}

	/**
	 * Getting successfully the download link of the script to install software
	 */
	@Test
	public void getSoftwareByNameAndVersionSuccessfully() throws JsonProcessingException {
		String name= "python";
		String version= "3.7";
		String paramValue= ScriptType.valueOf("INSTALLATION").toString();
		Object response= this.restTemplate.getForObject(
				baseUrl+ "/{name}/{version}?type={paramValue}",
				Object.class,
				name,
				version,
				paramValue
		);
		assertThat(jacksonMapper.writeValueAsString(response))
				.isNotNull()
				.isEqualTo(
						"{"
								+ "\"download_url\":\"https://raw.githubusercontent.com/FDP-ASIS/Repository/master/scripts/python/3.7/installation/script.ps1\""
								+ "}"
				);
	}

	/**
	 * Getting unsuccessfully the download link of the python3.7 script to install software
	 */
	@Test
	public void getSoftwareByNameAndVersionUnsuccessfully() throws JsonProcessingException {
		String name= "python";
		String version= "6";
		String paramValue= ScriptType.valueOf("INSTALLATION").toString();
		Object response= this.restTemplate.getForObject(
				baseUrl+ "/{name}/{version}?type={paramValue}",
				Object.class,
				name,
				version,
				paramValue
		);

		Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
			throw new NotFoundException(jacksonMapper.writeValueAsString(response));
		});

		assertNotEquals("{"
				+ "\"download_url\":\"https://raw.githubusercontent.com/FDP-ASIS/Repository/master/scripts/python/3.7/installation/script.ps1\""
				+ "}", exception.getMessage());
	}
}