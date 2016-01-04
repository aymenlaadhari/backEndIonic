package de.fiduciagad.sharea.server.data.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;

import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Account;

@Component
public class AccessTokenRepository extends AbstractRepository<AccessToken> {

	@Autowired
	public AccessTokenRepository(CouchDbConnector db) {
		super(AccessToken.class, db);
	}

	@View(name = "by_owningAccountId", map = "function(doc) { if(doc.docType === 'AccessToken' && doc.owningAccountId) {emit(doc.owningAccountId, doc._id)} }")
	public List<AccessToken> findByOwningAccount(Account account) {
		return queryView("by_owningAccountId", account.getId());
	}

	@View(name = "by_tokenText", map = "function(doc) { if(doc.docType === 'AccessToken' && doc.tokenText) {emit(doc.tokenText, doc._id)} }")
	public AccessToken findByTokenText(String token) {
		return Iterables.getFirst(queryView("by_tokenText", token), null);
	}

};