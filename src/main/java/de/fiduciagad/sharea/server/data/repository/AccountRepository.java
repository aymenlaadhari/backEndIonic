package de.fiduciagad.sharea.server.data.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;

import de.fiduciagad.sharea.server.data.repository.dto.Account;

@Component
public class AccountRepository extends AbstractRepository<Account> {

	@Autowired
	public AccountRepository(CouchDbConnector db) {
		super(Account.class, db);
	}

	@View(name = "by_email", map = "function(doc) { if(doc.docType === 'Account' && doc.email) {emit(doc.email, doc._id)} }")
	public Account findByEmail(String email) {
		return Iterables.getFirst(queryView("by_email", email), null);
	}

};