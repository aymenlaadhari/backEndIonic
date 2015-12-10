package de.fiduciagad.sharea.server.ws;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.dto.Person;
import de.fiduciagad.sharea.server.persistence.dao.PersonDaoIf;

@RestController
public class PersonEndpoint {

	
	@Autowired
	private PersonDaoIf personDao;
	
	
	
	@CrossOrigin
	@RequestMapping(value = "/personen", method = RequestMethod.GET)
	@ResponseBody
	public Person getPerson(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) throws IOException {
		
		Person person = personDao.readById("899cb7bfda45b838226232aa62d48288");
		
		return person;
	}
	

}