package de.fiduciagad.sharea.server.ws;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.repository.EventRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Event;
import de.fiduciagad.sharea.server.data.repository.dto.Location;

@RestController
public class EventEndpoint {

	private static final Log log = LogFactory.getLog(EventEndpoint.class);

	@Autowired
	private EventRepository eventRepository;

	@CrossOrigin
	@RequestMapping(value = "/createEvents", method = RequestMethod.POST, consumes = "application/json")
	public void createEvents(@RequestBody Event event) {
		eventRepository.add(event);
		log.info(event.getName() + " with ID: " + event.getId() + " created.");
	}

	@CrossOrigin
	@RequestMapping(value = "/events", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Event> getPerson(@RequestBody Location location) throws IOException {
		List<Event> listEvents = eventRepository.findByLocation(location);
		log.info(listEvents.size() + " elements for location: " + location.getLocation() + " found.");
		return listEvents;
	}

}
