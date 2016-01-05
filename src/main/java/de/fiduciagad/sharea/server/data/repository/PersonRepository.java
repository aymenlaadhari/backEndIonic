package de.fiduciagad.sharea.server.data.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;

import de.fiduciagad.sharea.server.data.repository.dto.Person;

@Component
public class PersonRepository extends AbstractRepository<Person> {

	@Autowired
	public PersonRepository(CouchDbConnector db) {
		super(Person.class, db);
	}

	@View(name = "by_name", map = "function(doc) { if(doc.docType === 'Person' && doc.name) {emit(doc.name, doc._id)} }")
	public Person findByName(String name) {
		return Iterables.getFirst(queryView("by_name", name), null);
	}

	@View(name = "by_owningAccountId", map = "function(doc) { if(doc.docType === 'Person' && doc.owningAccountId) {emit(doc.owningAccountId, doc._id)} }")
	public Person findByOwningAccountId(String owningAccountId) {
		return Iterables.getFirst(queryView("by_owningAccountId", owningAccountId), null);
	}

};