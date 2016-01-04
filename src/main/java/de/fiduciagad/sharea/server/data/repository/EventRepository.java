package de.fiduciagad.sharea.server.data.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.Event;

@Component
public class EventRepository extends AbstractRepository<Event> {

	@Autowired
	public EventRepository(CouchDbConnector db) {
		super(Event.class, db);
	}

	@View(name = "by_location", map = "function(doc) { if(doc.docType === 'Event' && doc.location) {emit(doc.location, doc._id)} }")
	public List<Event> findByLocation(String location) {
		return queryView("by_location", location);
	}

};