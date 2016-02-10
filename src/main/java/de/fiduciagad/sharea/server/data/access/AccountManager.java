package de.fiduciagad.sharea.server.data.access;

import java.security.AccessControlException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.google.common.collect.Sets;

import de.fiduciagad.sharea.server.data.exceptions.ModificationException;
import de.fiduciagad.sharea.server.data.repository.AccountRepository;
import de.fiduciagad.sharea.server.data.repository.PersonRepository;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Account;
import de.fiduciagad.sharea.server.data.repository.dto.Person;
import de.fiduciagad.sharea.server.security.TokenEnabledUserDetailsService;
import de.fiduciagad.sharea.server.security.User;

@Component
public class AccountManager extends AbstractManager<Account, AccountRepository> {

	private Log logger = LogFactory.getLog(AccountManager.class);

	@Autowired
	private AccessTokenManager accessTokenManager;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private TokenEnabledUserDetailsService userDetailsService;

	@Autowired
	Environment environment;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	public AccountManager(AccountRepository accountRepository) {
		super(accountRepository);
	}

	/**
	 * Adds a token to a user and returns the token value as String.
	 *
	 * WARNING! The user has to exist in the database!
	 *
	 * @param account
	 * @param deviceName
	 * @param deviceIdentifier
	 * @return
	 * @throws ModificationException
	 */
	public String addToken(Account account, String deviceName, String deviceIdentifier) throws ModificationException {
		try {
			AccessToken token = AccessToken.createRandom(deviceName, deviceIdentifier);
			token.setOwningAccountId(account.getId());
			accessTokenManager.create(token);
			return token.getTokenText();
		} catch (GeneralSecurityException e) {
			throw new ModificationException("Could not add token to user.", e);
		}

	}

	@Override
	protected void create(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		super.create(account);
		for (Person person : account.getPersons()) {
			person.setOwningAccountId(account.getId());
			personRepository.add(person);
		}
		for (AccessToken accessToken : account.getAccessTokens()) {
			accessToken.setOwningAccountId(account.getId());
			accessTokenManager.create(accessToken);
		}
	}

	private Account create(String email, String password, String realname, AccessToken token, String nickname) {
		String lowercaseEmail = email.toLowerCase();
		if (getRepository().findByEmail(lowercaseEmail) != null) {
			throw new DuplicateKeyException("User already exists.");
		}
		Account account = new Account(lowercaseEmail, password);
		Person person = new Person(realname);
		person.setNickname(nickname);
		account.getPersons().add(person);
		account.getAccessTokens().add(token);
		create(account);
		return account;
	}

	public String create(String email, String password, String realname, String deviceName, String deviceIdentifier,
			String nickname) throws GeneralSecurityException {
		AccessToken token = AccessToken.createRandom(deviceName, deviceIdentifier);
		create(email, password, realname, token, nickname);
		return token.getTokenText();
	}

	public void createDeveloperAccount(String name, String username, String tokenText, String nickname) {
		if (!environment.acceptsProfiles("dev") && !environment.acceptsProfiles("testing")) {
			throw new AccessControlException(
					"Method createDeveloperAccount() can only be called in dev or test environments! No Data changed.");
		}
		if (getRepository().findByEmail(username) != null) {
			logger.info("Skipping creation of developer account " + username + ". Account already exists.");
			return;
		}
		try {
			logger.info("Create developer account for: " + username);
			String password = "Start123";
			String deviceName = "Developer Device";
			AccessToken token = AccessToken.createRandom(deviceName, deviceName);
			token.setTokenText(tokenText);
			create(username, password, name, token, nickname);
		} catch (GeneralSecurityException e) {
			logger.error("Could not create developer account for: " + username);
		}
	}

	public boolean existsByEmail(String email) {
		return getRepository().findByEmail(email) != null;
	}

	/**
	 * Return the account without person or access token information. If you
	 * want to get associated persons and tokens use
	 * {@link #getAccountByEmail(String, boolean)}.
	 *
	 * @param email
	 * @return
	 */
	public Account getAccountByEmail(String email) {
		return getAccountByEmail(email, false);
	}

	/**
	 * Return the account.
	 *
	 * @param email
	 * @param eagerFetch
	 *            If set to <code>true</code> also return associated tokens an
	 *            persons, else these are omitted.
	 * @return
	 */
	public Account getAccountByEmail(String email, boolean eagerFetch) {
		Account account = getRepository().findByEmail(email);
		if (eagerFetch && account != null) {
			List<AccessToken> tokens = accessTokenManager.findByOwningAccount(account);
			account.setAccessTokens(Sets.newHashSet(tokens));
			Person persons = personRepository.findByOwningAccountId(account.getId());
			account.setPersons(Sets.newHashSet(persons));
		}
		return account;
	}

	public Account getAccountByToken(String tokenText) {
		AccessToken accessToken = accessTokenManager.findByTokenText(tokenText);
		if (accessToken != null) {
			try {
				return getRepository().get(accessToken.getOwningAccountId());
			} catch (NoDocumentException e) {
				return null;
			}
		}
		return null;
	}

	public User getUserByToken(String tokenText) {
		return userDetailsService.loadUserByToken(tokenText);
	}

	@Override
	public void update(Account account) {
		super.update(account);
		for (Person person : account.getPersons()) {
			person.setOwningAccountId(account.getId());
			personRepository.update(person);
		}
		for (AccessToken accessToken : account.getAccessTokens()) {
			accessToken.setOwningAccountId(account.getId());
			accessTokenManager.update(accessToken);
		}
	}

	public boolean validateUser(User user, String tokenText) {
		Account account = getAccountByToken(tokenText);
		return account != null && account.getId().equals(user.getAccount().getId());
	}

}
