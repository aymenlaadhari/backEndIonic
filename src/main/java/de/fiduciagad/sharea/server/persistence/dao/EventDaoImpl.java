package de.fiduciagad.sharea.server.persistence.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.dto.Event;
import de.fiduciagad.sharea.server.dto.Person;
import de.fiduciagad.sharea.server.enums.Location;
import de.fiduciagad.sharea.server.persistence.generic.DaoImpl;

@Component
public class EventDaoImpl extends DaoImpl<Event> implements EventDaoIf {

	public EventDaoImpl() {
		super(Event.class);
	}

	@Override
	public List<Event> readEventsByLocation(Location location) {

		List<Person> listPersons = new ArrayList<Person>();
		listPersons.add(new Person("Matthias"));
		listPersons.add(new Person("Rudi"));
		listPersons.add(new Person("Alex"));
		LocalDateTime localDateTime = LocalDateTime.now();

		List<Event> listEvents = new ArrayList<Event>();
		listEvents.add(new Event("Essen", Location.Frankfurt, listPersons, localDateTime.toString()));
		return listEvents;
	}

}
