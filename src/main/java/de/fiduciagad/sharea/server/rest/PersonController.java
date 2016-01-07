package de.fiduciagad.sharea.server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	@CrossOrigin
	@RequestMapping(value = "/api/v1/person", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Person getPerson(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return personManager.findByAccount(user.getAccount());
	}

	@CrossOrigin
	@RequestMapping(value = "/api/v1/person/{id}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Person getPerson(@PathVariable String id) {
		return personManager.get(id);
	}

}
