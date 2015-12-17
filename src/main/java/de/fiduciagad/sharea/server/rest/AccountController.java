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

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.ibm.watson.developer_cloud.service.InternalServerErrorException;

import de.fiduciagad.sharea.server.data.repository.AccessTokenRepository;
import de.fiduciagad.sharea.server.data.repository.AccountRepository;
import de.fiduciagad.sharea.server.data.repository.PersonRepository;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Account;
import de.fiduciagad.sharea.server.data.repository.dto.Person;

@RestController
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private AccessTokenRepository accessTokenRepository;

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
	public Map<String, Object> createUser(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "realname", required = true) String realname,
			@RequestParam(value = "deviceName", required = true) String deviceName,
			@RequestParam(value = "deviceIdentifier", required = true) String deviceIdentifier) {

		if (accountRepository.findByEmail(email) != null) {
			throw new DuplicateKeyException("User already exists.");
		}
		Account account = new Account(email, passwordEncoder.encode(password));
		accountRepository.add(account);

		if (!Strings.isNullOrEmpty(account.getId())) {
			Person person = new Person(realname);
			person.setOwningAccountId(account.getId());
			personRepository.add(person);

			try {
				AccessToken currentToken = AccessToken.createRandom(deviceName, deviceIdentifier);
				currentToken.setDeviceName(deviceName);
				currentToken.setOwningAccountId(account.getId());
				accessTokenRepository.add(currentToken);
				// TODO send mail
				HashMap<String, Object> response = Maps.newHashMap();
				response.put("success", Boolean.TRUE);
				response.put("auth-token", currentToken.getTokenText());
				return response;
			} catch (GeneralSecurityException e) {
				throw new InternalServerErrorException("Cannot create token for user. ", e);
			}
		} else {
			throw new InternalServerErrorException("Could not create account.");
		}
	}

}
