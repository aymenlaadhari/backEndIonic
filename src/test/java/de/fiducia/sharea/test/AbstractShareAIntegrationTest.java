package de.fiducia.sharea.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;

import de.fiduciagad.sharea.server.App;
import de.fiduciagad.sharea.server.data.access.AccountManager;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebIntegrationTest({ "server.port=0", "spring.profiles.active=testing" })
public class AbstractShareAIntegrationTest {

	@Autowired
	private AccountManager accountManager;

	@Value("${local.server.port}")
	private int port;

	public AbstractShareAIntegrationTest() {
	}

	@Before
	public void abstractSetup() {
		accountManager.createDeveloperAccount("Testus Juser", "none@example.com",
				"f0bf325a642c300614f31a72084c5ef11609a1ae0a0fba52", "testuser");

		RestAssured.requestSpecification = new RequestSpecBuilder()
				.addHeader("X-AUTH-TOKEN", "f0bf325a642c300614f31a72084c5ef11609a1ae0a0fba52").setPort(port).build();
	}

	@After
	public void abstractTearDown() {
		// TODO Delete data and stuff...
	}

}
