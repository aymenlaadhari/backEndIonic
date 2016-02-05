package de.fiduciagad.sharea.server.rest;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.access.PersonManager;
import de.fiduciagad.sharea.server.data.repository.dto.Person;
import de.fiduciagad.sharea.server.security.User;

@RestController
public class PersonController {

	public static final String API_PERSON = "/api/" + AbstractController.API_VERSION + "/person";

	public static final String API_PERSON_RANDOM = "/api/" + AbstractController.API_VERSION + "/person/random";

	@Autowired
	private PersonManager personManager;

	@RequestMapping(value = API_PERSON, method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Person getAuthenticatedPerson(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return personManager.findByAccount(user.getAccount());
	}

	@RequestMapping(value = API_PERSON + "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Person getPersonById(@PathVariable String id) {
		return personManager.get(id);
	}

	@ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "dev")
	@RequestMapping(value = API_PERSON_RANDOM, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Person getRandomPerson() {
		// TODO XXX remove!!!
		List<Person> all = personManager.getAll();
		return all.get(new Random().nextInt(all.size()));
	}

}
