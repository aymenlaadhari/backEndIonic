package de.fiduciagad.sharea.server.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.AccessTokenRepository;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;

@Component
public class AccessTokenManager extends AbstractManager<AccessToken, AccessTokenRepository> {

	@Autowired
	public AccessTokenManager(AccessTokenRepository repository) {
		super(repository);
	}

}
