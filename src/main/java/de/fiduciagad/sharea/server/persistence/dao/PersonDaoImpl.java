package de.fiduciagad.sharea.server.persistence.dao;

import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.dto.Person;
import de.fiduciagad.sharea.server.persistence.generic.DaoImpl;

@Component
public class PersonDaoImpl extends DaoImpl<Person> implements PersonDaoIf {

	public PersonDaoImpl() {
		super(Person.class);
	}

}
