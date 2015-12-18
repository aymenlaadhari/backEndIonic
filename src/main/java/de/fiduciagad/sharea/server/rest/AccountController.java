package de.fiduciagad.sharea.server.rest;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.ibm.watson.developer_cloud.service.InternalServerErrorException;

import de.fiduciagad.sharea.server.data.dao.AccountManager;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Account;
import de.fiduciagad.sharea.server.data.repository.dto.Person;

@RestController
public class AccountController {

	@Autowired
	private AccountManager accountManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Create a new account
	 *
	 * @param email
	 * @param password
	 * @param realname
	 * @param deviceName
	 *            A readable device name which is identifiable by the user (e.g.
	 *            "iPhone 6").
	 * @return A valid token for the device.
	 */
	@CrossOrigin
	@RequestMapping(value = "/api/v1/account", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, Object> createUser(@RequestParam(required = true) String email,
			@RequestParam(required = true) String password, @RequestParam(required = true) String realname,
			@RequestParam(required = true) String deviceName, @RequestParam(required = true) String deviceIdentifier) {

		if (accountManager.getAccountByEmail(email) != null) {
			throw new DuplicateKeyException("User already exists.");
		}

		try {
			Account account = new Account(email, passwordEncoder.encode(password));
			Person person = new Person(realname);
			account.getPersons().add(person);
			AccessToken currentToken = AccessToken.createRandom(deviceName, deviceIdentifier);
			currentToken.setDeviceName(deviceName);
			account.getAccessTokens().add(currentToken);
			accountManager.create(account);

			// TODO send mail
			HashMap<String, Object> response = Maps.newHashMap();
			response.put("success", true);
			response.put("auth-token", currentToken.getTokenText());
			return response;
		} catch (GeneralSecurityException e) {
			throw new InternalServerErrorException("Cannot create token for user. ", e);
		}
	}

}
