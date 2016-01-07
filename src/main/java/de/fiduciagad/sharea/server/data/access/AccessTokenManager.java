package de.fiduciagad.sharea.server.data.access;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.AccessTokenRepository;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Account;

@Component
public class AccessTokenManager extends AbstractManager<AccessToken, AccessTokenRepository> {

	@Autowired
	public AccessTokenManager(AccessTokenRepository repository) {
		super(repository);
	}

	public List<AccessToken> findByOwningAccount(Account account) {
		return getRepository().findByOwningAccount(account);
	}

	public AccessToken findByTokenText(String tokenText) {
		return getRepository().findByTokenText(tokenText);
	}

}
