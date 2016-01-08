package de.fiduciagad.sharea.server.rest;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.access.AccountManager;
import de.fiduciagad.sharea.server.data.exceptions.ModificationException;
import de.fiduciagad.sharea.server.data.repository.dto.Account;
import de.fiduciagad.sharea.server.rest.dto.NewToken;

@RestController
public class TokenController {

	@Autowired
	private AccountManager accountManager;

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
	public Map<String, String> createToken(@RequestBody NewToken newToken) throws ModificationException {

		Account account = accountManager.getAccountByEmail(newToken.getEmail(), true);
		if (account != null && passwordEncoder.matches(newToken.getPassword(), account.getPassword())) {
			String tokenText = accountManager.addToken(account, newToken.getDeviceName(),
					newToken.getDeviceIdentifier());
			return Collections.singletonMap("auth-token", tokenText);
		}
		throw new BadCredentialsException("Could not authenticate user.");
	}

}
