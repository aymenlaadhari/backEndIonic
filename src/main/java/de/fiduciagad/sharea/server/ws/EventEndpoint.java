package de.fiduciagad.sharea.server.ws;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudant.client.api.model.Document;

import de.fiduciagad.sharea.server.dto.Event;
import de.fiduciagad.sharea.server.enums.Location;
import de.fiduciagad.sharea.server.persistence.dao.EventDaoIf;

@RestController
public class EventEndpoint {
	
	private static final Log log = LogFactory.getLog(EventEndpoint.class);

	@Autowired
	private EventDaoIf eventDao;
	
	@CrossOrigin
	@RequestMapping(value = "/events", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Event> getPerson(@RequestParam(value = "event", required = false, defaultValue = "Frankfurt") Location location)
			throws IOException {

		List<Event> listEvents = eventDao.readEventsByLocation(location);

		List<Document> listeDocs = eventDao.getAllDocs();
		
		for (Document document : listeDocs) {
			
			log.info(document.toString());
			
		}
		
		return listEvents;
	}

}
