package de.fiduciagad.sharea.server.persistence.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.dto.Event;
import de.fiduciagad.sharea.server.dto.Location;
import de.fiduciagad.sharea.server.persistence.generic.DaoImpl;

@Component
public class EventDaoImpl extends DaoImpl<Event> implements EventDaoIf {

	public EventDaoImpl() {
		super(Event.class);
	}

	@Override
	public List<Event> readEventsByLocation(Location location) {
		return read("\"selector\": {\"location\": {\"location\": \"" + location.getLocation() + "\"}}");
	}

}
