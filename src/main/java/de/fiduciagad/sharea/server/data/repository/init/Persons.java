package de.fiduciagad.sharea.server.data.repository.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import de.fiduciagad.sharea.server.data.repository.PersonRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Person;

@Component
public class Persons {

	@Autowired
	private PersonRepository personRepository;

	public Persons() {
		// TODO Auto-generated constructor stub
	}

	public List<String> init() {
		ArrayList<String> examplePersonIds = Lists.newArrayList();

		for (Person person : personRepository.getAll()) {
			examplePersonIds.add(person.getNickname());
		}
		return examplePersonIds;

	}

}
