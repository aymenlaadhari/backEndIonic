package de.fiducia.sharea.test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;

import de.fiduciagad.sharea.server.App;
import de.fiduciagad.sharea.server.data.repository.PersonRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Person;
import de.fiduciagad.sharea.server.rest.PersonController;

@IntegrationTest({ "server.port:9090", "management.port:9091", "spring.profiles.active=dev" })
@SpringApplicationConfiguration(classes = App.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PersonTest {

	@Autowired
	private PersonRepository personRepository;

	private Person testPerson = new Person("Test User");

	@Before
	public void setUp() {
		// Use the test user token.
		RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("X-AUTH-TOKEN",
				"f0bf325a642c300614f31a72084c5ef11609a1ae0a0fba52").build();
		personRepository.add(testPerson);
	}

	@After
	public void tearDown() {
		personRepository.remove(testPerson);
	}

	@Test
	public void testGetAuthenticatedPersonSchema() {
		when().get(PersonController.API_PERSON)//
				.then()//
				.assertThat()//
				.statusCode(HttpStatus.OK.value())//
				.and()//
				.body(matchesJsonSchema(getClass().getResource("person-schema.json")));
	}

	@Test
	public void testGetCurrentlyAuthenticatedUser() {
		when()//
		.get(PersonController.API_PERSON)//
				.then()//
				.assertThat()//
				.statusCode(HttpStatus.OK.value())//
				.and()//
				.body("name", equalTo("Testus Juser"));
	}

	@Test
	public void testRandomPersonSchema() {
		when()//
		.get(PersonController.API_PERSON_RANDOM)//
				.then()//
				.assertThat()//
				.statusCode(HttpStatus.OK.value())//
				.and()//
				.body(matchesJsonSchema(getClass().getResource("person-schema.json")));
	}

	@Test
	public void testTestUserDetails() {
		given()//
		.pathParam("id", testPerson.getId())//
				.when().get(PersonController.API_PERSON_BY_ID)//
				.then()//
				.assertThat()//
				.statusCode(HttpStatus.OK.value())//
				.and()//
				.body("name", equalTo(testPerson.getName()));
	}

}
