package de.fiduciagad.sharea.server.data.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.Location;

@Component
@View(name = "all", map = "function(doc) { if (doc.type === 'Location' ) emit( null, doc._id )}")
public class LocationRepository extends CouchDbRepositorySupport<Location> {

	protected LocationRepository(Class<Location> type, CouchDbConnector db) {
		super(Location.class, db);
		initStandardDesignDocument();
	}

	@Autowired
	public LocationRepository(CouchDbConnector db) {
		this(Location.class, db);
	}

};