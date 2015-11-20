package de.fiduciagad.sharea.server.ws;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.dto.Event;
import de.fiduciagad.sharea.server.enums.Location;
import de.fiduciagad.sharea.server.persistence.dao.EventDaoIf;

@RestController
public class EventEndpoint {

	@Autowired
	private EventDaoIf eventDao;
	
	@CrossOrigin
	@RequestMapping(value = "/events", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Event> getPerson(@RequestParam(value = "event", required = false, defaultValue = "Frankfurt") Location location)
			throws IOException {

		List<Event> listEvents = eventDao.readEventsByLocation(location);
		

		return listEvents;
	}

}
