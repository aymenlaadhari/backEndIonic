package de.fiduciagad.sharea.server.data.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;

import de.fiduciagad.sharea.server.data.repository.dto.Person;

@Component
public class PersonRepository extends AbstractRepository<Person> {

	private static final String BY_OWNING_ACCOUNT_ID = "by_owningAccountId";
	private static final String BY_NAME = "by_name";

	@Autowired
	public PersonRepository(CouchDbConnector db) {
		super(Person.class, db);
	}

	@View(name = BY_NAME, map = "function(doc) { if(doc.docType === 'Person' && doc.name) {emit(doc.name, doc._id)} }")
	public Person findByName(String name) {
		return Iterables.getFirst(queryView(BY_NAME, name), null);
	}

	@View(name = BY_OWNING_ACCOUNT_ID, map = "function(doc) { if(doc.docType === 'Person' && doc.owningAccountId) {emit(doc.owningAccountId, doc._id)} }")
	public Person findByOwningAccountId(String owningAccountId) {
		return Iterables.getFirst(queryView(BY_OWNING_ACCOUNT_ID, owningAccountId), null);
	}

};