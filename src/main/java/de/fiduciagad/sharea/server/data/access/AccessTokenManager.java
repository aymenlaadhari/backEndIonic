package de.fiduciagad.sharea.server.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.AccessTokenRepository;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;

@Component
public class AccessTokenManager {

	@Autowired
	private AccessTokenRepository accessTokenRepository;

	public AccessTokenManager() {
	}

	public void create(AccessToken comment) {
		accessTokenRepository.add(comment);
	}

	public AccessToken get(String id) {
		return accessTokenRepository.get(id);
	}

}
