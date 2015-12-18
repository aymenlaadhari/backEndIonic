package de.fiduciagad.sharea.server.data.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.Event;

@Component
@View(name = "all", map = "function(doc) { if (doc.docType === 'Event' ) emit( null, doc._id )}")
public class EventRepository extends CouchDbRepositorySupport<Event> {

	protected EventRepository(Class<Event> type, CouchDbConnector db) {
		super(Event.class, db);
		initStandardDesignDocument();
	}

	@Autowired
	public EventRepository(CouchDbConnector db) {
		this(Event.class, db);
	}

	@View(name = "by_location", map = "function(doc) { if(doc.docType === 'Event' && doc.location) {emit(doc.location, doc._id)} }")
	public List<Event> findByLocation(String location) {
		return queryView("by_location", location);
	}

};