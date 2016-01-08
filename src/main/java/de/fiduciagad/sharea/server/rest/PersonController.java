package de.fiduciagad.sharea.server.rest;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private PersonManager personManager;

	@RequestMapping(value = "/api/v1/person", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Person getPerson(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return personManager.findByAccount(user.getAccount());
	}

	@RequestMapping(value = "/api/v1/person/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Person getPerson(@PathVariable String id) {
		return personManager.get(id);
	}

	@RequestMapping(value = "/api/v1/person/random", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Person getPerson() {
		List<Person> all = personManager.getAll();
		return all.get(new Random().nextInt(all.size()));
	}

}
