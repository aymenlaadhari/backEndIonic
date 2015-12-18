package de.fiduciagad.sharea.server.rest;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import de.fiduciagad.sharea.server.data.access.AccountManager;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Account;
import de.fiduciagad.sharea.server.data.repository.dto.Person;
import de.fiduciagad.sharea.server.rest.dto.NewAccount;

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
	public Map<String, Object> createAccout(@RequestBody(required = true) NewAccount newAccount) {

		// TODO Beautify email vaidation
		if (!EmailValidator.getInstance().isValid(newAccount.getEmail())) {
			throw new IllegalArgumentException("The email address is not valid.");
		}
		if (!newAccount.getEmail().endsWith("@fiduciagad.de")) {
			throw new IllegalArgumentException("You have to use an @fiduciagad.de e-mail address.");
		}
		if (accountManager.getAccountByEmail(newAccount.getEmail()) != null) {
			throw new DuplicateKeyException("User already exists.");
		}

		try {
			Account account = new Account(newAccount.getEmail(), passwordEncoder.encode(newAccount.getPassword()));
			Person person = new Person(newAccount.getRealname());
			account.getPersons().add(person);
			AccessToken currentToken = AccessToken.createRandom(newAccount.getDeviceName(),
					newAccount.getDeviceIdentifier());
			account.getAccessTokens().add(currentToken);
			accountManager.create(account);

			// TODO send mail
			HashMap<String, Object> response = Maps.newHashMap();
			response.put("success", true);
			response.put("auth-token", currentToken.getTokenText());
			return response;
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Cannot create token for user. ", e);
		}
	}

}
