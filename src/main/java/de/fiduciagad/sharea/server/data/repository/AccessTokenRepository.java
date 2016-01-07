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

	private static final String BY_TOKEN_TEXT = "by_tokenText";
	private static final String BY_OWNING_ACCOUNT_ID = "by_owningAccountId";

	@Autowired
	public AccessTokenRepository(CouchDbConnector db) {
		super(AccessToken.class, db);
	}

	@View(name = BY_OWNING_ACCOUNT_ID, map = "function(doc) { if(doc.docType === 'AccessToken' && doc.owningAccountId) {emit(doc.owningAccountId, doc._id)} }")
	public List<AccessToken> findByOwningAccount(Account account) {
		return queryView(BY_OWNING_ACCOUNT_ID, account.getId());
	}

	@View(name = BY_TOKEN_TEXT, map = "function(doc) { if(doc.docType === 'AccessToken' && doc.tokenText) {emit(doc.tokenText, doc._id)} }")
	public AccessToken findByTokenText(String token) {
		return Iterables.getFirst(queryView(BY_TOKEN_TEXT, token), null);
	}

};