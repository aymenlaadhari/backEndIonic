package de.fiduciagad.sharea.server.data.access;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.EventRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Event;

@Component
public class EventManager extends AbstractManager<Event, EventRepository> {

	@Autowired
	public EventManager(EventRepository eventRepository) {
		super(eventRepository);
	}

	public void create(String name, String location, Set<String> participantIds, Date dateTime) {
		Event event = new Event(name, location, participantIds, dateTime);
		create(event);
	}

	public List<Event> findByStartLocation(String location, int limit) {
		return getRepository().findByLocation(location);
	}

}
