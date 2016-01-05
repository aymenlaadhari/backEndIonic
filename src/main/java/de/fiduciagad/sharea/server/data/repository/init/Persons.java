package de.fiduciagad.sharea.server.data.repository.init;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import de.fiduciagad.sharea.server.data.repository.PersonRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Person;

@Component
public class Persons {

	private Log logger = LogFactory.getLog(Categories.class);

	@Autowired
	private PersonRepository personRepository;

	public Persons() {
		// TODO Auto-generated constructor stub
	}

	public List<String> init() {
		ArrayList<String> examplePersonIds = Lists.newArrayList();
		ArrayList<Person> persons = Lists.newArrayList(new Person("Adrian Rochau-Beispiel"),
				new Person("Alexander Widak-Beispiel"), new Person("Rudolf Mottinger-Beispiel"),
				new Person("Matthias Weber-Beispiel"), new Person("Maximilian Geißel-Beispiel"),
				new Person("Jens Zimmermann-Beispiel"));

		for (Person person : persons) {
			Person dbResult = personRepository.findByName(person.getName());
			if (dbResult == null) {
				logger.info("Add example person: " + person.getName());
				personRepository.add(person);
				examplePersonIds.add(person.getId());
			} else {
				logger.info("Did not add example person: " + dbResult.getName());
				examplePersonIds.add(dbResult.getId());
			}
		}
		return examplePersonIds;

	}

}
