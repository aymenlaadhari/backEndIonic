package de.fiduciagad.sharea.server.rest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.access.AccessTokenManager;
import de.fiduciagad.sharea.server.data.access.AccountManager;
import de.fiduciagad.sharea.server.data.exceptions.ModificationException;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Account;
import de.fiduciagad.sharea.server.data.repository.dto.Person;
import de.fiduciagad.sharea.server.rest.dto.NewToken;

@RestController
public class TokenController {

	public static final String API_TOKEN_RANDOM = "/api/v1/token/random";

	@Autowired
	private AccountManager accountManager;

	@Autowired
	private AccessTokenManager accessTokenManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Creates a new token for an existing user.
	 *
	 * @param username
	 * @param password
	 * @param deviceName
	 * @return
	 * @throws ModificationException
	 */
	@RequestMapping(value = "/api/v1/token", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> createToken(@RequestBody(required = true) NewToken newToken) throws ModificationException {
		Account account = accountManager.getAccountByEmail(newToken.getEmail(), true);
		if(null == account) {
			throw new BadCredentialsException("No such account.");
		}
		if (passwordEncoder.matches(newToken.getPassword(), account.getPassword())) {
			String tokenText = accountManager.addToken(account, newToken.getDeviceName(),
					newToken.getDeviceIdentifier());
			return Collections.singletonMap("auth-token", tokenText);
		}
		throw new BadCredentialsException("Could not authenticate user.");
	}

	@RequestMapping(value = API_TOKEN_RANDOM, method = RequestMethod.POST, consumes = "application/json")
	public String getRandomToken(@RequestBody Person person) {
		// TODO XXX remove!!!
		List<AccessToken> all = accessTokenManager.findByOwningAccount(accountManager.get(person.getOwningAccountId()));
		return all.get(new Random().nextInt(all.size())).getTokenText();
	}
}
