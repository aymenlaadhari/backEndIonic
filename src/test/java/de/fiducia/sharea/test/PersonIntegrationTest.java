package de.fiducia.sharea.test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import de.fiduciagad.sharea.server.data.repository.PersonRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Person;
import de.fiduciagad.sharea.server.rest.PersonController;

public class PersonIntegrationTest extends AbstractShareAIntegrationTest {

	@Autowired
	private PersonRepository personRepository;

	private Person testPerson = new Person("Test User");

	@Before
	public void setUp() {
		// Use the test user token.

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
