package de.fiduciagad.sharea.server.data.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.Event;
import de.fiduciagad.sharea.server.data.repository.dto.Location;

@Component
@View(name = "all", map = "function(doc) { if (doc.type === 'Event' ) emit( null, doc._id )}")
public class EventRepository extends CouchDbRepositorySupport<Event> {

	protected EventRepository(Class<Event> type, CouchDbConnector db) {
		super(Event.class, db);
		initStandardDesignDocument();
	}

	@Autowired
	public EventRepository(CouchDbConnector db) {
		this(Event.class, db);
	}

	public List<Event> findByLocation(Location location) {
		return queryView("by_location", ComplexKey.of(location));
	}

};