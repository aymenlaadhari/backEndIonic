package de.fiduciagad.sharea.server.ws;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.dto.Person;
import de.fiduciagad.sharea.server.dto.Share;
import de.fiduciagad.sharea.server.persistence.dao.PersonDaoIf;

@RestController
public class PersonEndpoint {

	
	@Autowired
	private PersonDaoIf personDao;
	
	
	
	@CrossOrigin
	@RequestMapping(value = "/api/v1/getPersonByID", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Person getPerson(@RequestBody String _id) throws IOException {
		
		Person person = personDao.readById(_id);
		
		return person;
	}
	

}