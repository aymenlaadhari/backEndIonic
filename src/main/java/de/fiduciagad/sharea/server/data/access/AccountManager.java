package de.fiduciagad.sharea.server.data.access;

import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.google.common.collect.Sets;

import de.fiduciagad.sharea.server.data.repository.AccessTokenRepository;
import de.fiduciagad.sharea.server.data.repository.AccountRepository;
import de.fiduciagad.sharea.server.data.repository.PersonRepository;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Account;
import de.fiduciagad.sharea.server.data.repository.dto.Person;
import de.fiduciagad.sharea.server.dto.exceptions.ModificationException;
import de.fiduciagad.sharea.server.security.TokenEnabledUserDetailsService;
import de.fiduciagad.sharea.server.security.User;

@Component
public class AccountManager {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccessTokenRepository accessTokenRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private TokenEnabledUserDetailsService userDetailsService;

	public AccountManager() {
	}

	/**
	 * Adds a token to a user and returns the token value as String.
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
			accessTokenRepository.add(token);
			return token.getTokenText();
		} catch (GeneralSecurityException e) {
			throw new ModificationException("Could not add token to user.", e);
		}

	}

	public void create(Account account) {
		accountRepository.add(account);
		for (Person person : account.getPersons()) {
			person.setOwningAccountId(account.getId());
			personRepository.add(person);
		}
		for (AccessToken accessToken : account.getAccessTokens()) {
			accessToken.setOwningAccountId(account.getId());
			accessTokenRepository.add(accessToken);
		}
	}

	public boolean exists(String email) {
		return accountRepository.findByEmail(email) != null;
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
		Account account = accountRepository.findByEmail(email);
		if (eagerFetch && account != null) {
			List<AccessToken> tokens = accessTokenRepository.findByOwningAccount(account);
			account.setAccessTokens(Sets.newHashSet(tokens));
			Person persons = personRepository.findByOwningAccountId(account.getId());
			account.setPersons(Sets.newHashSet(persons));
		}
		return account;
	}

	public Account getAccountByToken(String tokenText) {
		AccessToken accessToken = accessTokenRepository.findByTokenText(tokenText);
		if (accessToken != null) {
			try {
				return accountRepository.get(accessToken.getOwningAccountId());
			} catch (NoDocumentException e) {
				return null;
			}
		}
		return null;
	}

	public User getUserByToken(String tokenText) {
		return userDetailsService.loadUserByToken(tokenText);
	}

	public boolean validateUser(User user, String tokenText) {
		Account account = getAccountByToken(tokenText);
		return account != null && account.getId().equals(user.getAccount().getId());
	}

}
