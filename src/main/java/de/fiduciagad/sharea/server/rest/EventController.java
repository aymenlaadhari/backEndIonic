package de.fiduciagad.sharea.server.rest;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.access.EventManager;
import de.fiduciagad.sharea.server.data.repository.dto.Event;

@RestController
public class EventController {

	@Autowired
	private EventManager eventManager;

	@CrossOrigin
	@RequestMapping(value = "/api/v1/event", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, Object> createEvent(@RequestParam(required = true) String name,
			@RequestParam(required = true) String location, @RequestParam(required = true) Set<String> participantIds,
			@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startTime) {

		eventManager.create(name, location, participantIds, startTime);
		// TODO ehm... if it doesn't throw an exception everything is cool?
		// Hmm... maybe better error handling.
		return Collections.singletonMap("success", true);
	}

	@CrossOrigin
	@RequestMapping(value = "/api/v1/event", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Event> findEvents(@RequestParam(required = true) String location,
			@RequestParam(required = true) int limit) {
		return eventManager.findByStartLocation(location, limit);
	}

	@CrossOrigin
	@RequestMapping(value = "/api/v1/event/{id}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Event getEvent(@PathVariable String id) {
		return eventManager.get(id);
	}

}
