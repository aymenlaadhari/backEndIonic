package de.fiduciagad.sharea.server.rest;

import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.access.AccountManager;
import de.fiduciagad.sharea.server.rest.dto.NewAccount;

@RestController
public class AccountController {

	@Autowired
	private AccountManager accountManager;

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
	public Map<String, String> createAccount(@RequestBody(required = true) NewAccount newAccount) {

		// TODO Beautify email vaidation
		if (!EmailValidator.getInstance().isValid(newAccount.getEmail())) {
			throw new IllegalArgumentException("The email address is not valid.");
		}
		if (!newAccount.getEmail().endsWith("@fiduciagad.de")) {
			throw new IllegalArgumentException("You have to use an @fiduciagad.de e-mail address.");
		}
		if (accountManager.existsByEmail(newAccount.getEmail())) {
			throw new DuplicateKeyException("User already exists.");
		}

		try {
			String token = accountManager.create(newAccount.getEmail(), newAccount.getPassword(),
					newAccount.getRealname(), newAccount.getDeviceName(), newAccount.getDeviceIdentifier());

			// TODO send mail
			return Collections.singletonMap("auth-token", token);
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Cannot create token for user. ", e);
		}
	}

}
