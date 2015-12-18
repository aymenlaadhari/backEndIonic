package de.fiduciagad.sharea.server.rest;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import de.fiduciagad.sharea.server.data.repository.AccessTokenRepository;
import de.fiduciagad.sharea.server.data.repository.AccountRepository;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Account;
import de.fiduciagad.sharea.server.rest.dto.NewToken;

@RestController
public class TokenController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccessTokenRepository accessTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Creates a new token for an existing user.
	 *
	 * @param username
	 * @param password
	 * @param deviceName
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "/api/v1/token", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, Object> login(@RequestBody NewToken newToken) {

		Account account = accountRepository.findByEmail(newToken.getEmail());
		if (account != null && passwordEncoder.matches(newToken.getPassword(), account.getPassword())) {
			AccessToken currentToken;
			try {
				currentToken = AccessToken.createRandom(newToken.getDeviceName(), newToken.getDeviceIdentifier());
				currentToken.setOwningAccountId(account.getId());
				accessTokenRepository.add(currentToken);

				HashMap<String, Object> httpResponse = Maps.newHashMap();
				httpResponse.put("success", Boolean.TRUE);
				httpResponse.put("auth-token", currentToken.getTokenText());
				return httpResponse;
			} catch (GeneralSecurityException e) {
				throw new IllegalStateException("Cannot create token for user. ", e);
			}
		}
		throw new BadCredentialsException("Could not authenticate user.");
	}

}
