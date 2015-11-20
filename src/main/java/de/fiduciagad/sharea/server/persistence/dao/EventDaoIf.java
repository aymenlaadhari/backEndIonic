package de.fiduciagad.sharea.server.persistence.dao;

import java.util.List;

import de.fiduciagad.sharea.server.dto.Event;
import de.fiduciagad.sharea.server.enums.Location;
import de.fiduciagad.sharea.server.persistence.generic.Dao;

public interface EventDaoIf extends Dao<Event>{

	
	public List<Event> readEventsByLocation(Location location);

}
