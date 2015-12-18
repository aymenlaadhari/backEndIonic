package de.fiduciagad.sharea.server.data.access;

import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudant.client.org.lightcouch.NoDocumentException;

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

	public void addToken(Account account, String deviceName, String deviceIdentifier) throws ModificationException {
		try {
			AccessToken token = getNewToken(deviceName, deviceIdentifier);
			token.setOwningAccountId(account.getId());
			accessTokenRepository.add(token);
		} catch (GeneralSecurityException e) {
			throw new ModificationException("Could not add token to user.", e);
		}

	}

	public void addToken(User user, String deviceName, String deviceIdentifier) throws ModificationException {
		addToken(user.getAccount(), deviceName, deviceIdentifier);
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

	public Account getAccountByEmail(String email) {
		return accountRepository.findByEmail(email);
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

	private AccessToken getNewToken(String deviceName, String deviceIdentifier) throws GeneralSecurityException {
		return AccessToken.createRandom(deviceName, deviceIdentifier);
	}

	public User getUserByToken(String tokenText) {
		return userDetailsService.loadUserByToken(tokenText);
	}

	public boolean validateUser(User user, String tokenText) {
		Account account = getAccountByToken(tokenText);
		return account != null && account.getId().equals(user.getAccount().getId());
	}

}
