package de.fiduciagad.sharea.server.rest;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.repository.AccessTokenRepository;
import de.fiduciagad.sharea.server.data.repository.PersonRepository;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Person;

@RestController
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private AccessTokenRepository accessTokenRepository;

	@CrossOrigin
	@RequestMapping(value = "/api/v1/person/{id}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Person getPerson(@PathVariable String id) {
		return personRepository.get(id);
	}

	@CrossOrigin
	@RequestMapping(value = "/api/v1/person", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Person getPersonByToken(@RequestHeader(name = "X-AUTH-TOKEN", required = true) String token,
			HttpServletResponse httpResponse) {
		AccessToken accessToken = accessTokenRepository.get(token);
		if (accessToken != null) {
			return personRepository.findByOwningAccountId(accessToken.getOwningAccountId());
		}
		httpResponse.setStatus(HttpStatus.SC_NOT_FOUND);
		return null;
	}

}
