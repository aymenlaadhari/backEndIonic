package de.fiduciagad.sharea.server.data.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.EventRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Event;

@Component
public class EventManager {

	@Autowired
	private EventRepository eventRepository;

	public EventManager() {
	}

	public void create(Event event) {
		eventRepository.add(event);
	}

	public void create(String name, String location, Set<String> participantIds, Date dateTime) {
		Event event = new Event(name, location, participantIds, dateTime);
		eventRepository.add(event);
	}

	public List<Event> findByStartLocation(String location, int limit) {
		return eventRepository.findByLocation(location);
	}

	public Event get(String id) {
		return eventRepository.get(id);
	}

}
