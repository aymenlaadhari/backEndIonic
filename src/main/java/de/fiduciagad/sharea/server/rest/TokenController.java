package de.fiduciagad.sharea.server.rest;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.ibm.watson.developer_cloud.service.InternalServerErrorException;

import de.fiduciagad.sharea.server.data.repository.AccessTokenRepository;
import de.fiduciagad.sharea.server.data.repository.AccountRepository;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Account;

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
	public Map<String, Object> login(@RequestParam(required = true) String email,
			@RequestParam(required = true) String password, @RequestParam(required = true) String deviceName,
			@RequestParam(required = true) String deviceIdentifier) {

		Account account = accountRepository.findByEmail(email);
		if (account != null && passwordEncoder.matches(password, account.getPassword())) {
			AccessToken currentToken;
			try {
				currentToken = AccessToken.createRandom(deviceName, deviceIdentifier);
				currentToken.setOwningAccountId(account.getId());
				accessTokenRepository.add(currentToken);

				HashMap<String, Object> httpResponse = Maps.newHashMap();
				httpResponse.put("success", Boolean.TRUE);
				httpResponse.put("auth-token", currentToken.getTokenText());
				return httpResponse;
			} catch (GeneralSecurityException e) {
				throw new InternalServerErrorException("Cannot create token for user. ", e);
			}
		}
		throw new BadCredentialsException("Could not authenticate user.");
	}

}
