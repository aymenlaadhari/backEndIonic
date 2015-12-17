package de.fiduciagad.sharea.server.data.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.Person;

@Component
@View(name = "all", map = "function(doc) { if (doc.type === 'Person' ) emit( null, doc._id )}")
public class PersonRepository extends CouchDbRepositorySupport<Person> {

	protected PersonRepository(Class<Person> type, CouchDbConnector db) {
		super(Person.class, db);
		initStandardDesignDocument();
	}

	@Autowired
	public PersonRepository(CouchDbConnector db) {
		this(Person.class, db);
	}

};