package de.fiduciagad.sharea.server.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.AccessTokenRepository;
import de.fiduciagad.sharea.server.data.repository.PersonRepository;
import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;
import de.fiduciagad.sharea.server.data.repository.dto.Account;
import de.fiduciagad.sharea.server.data.repository.dto.Person;

@Component
public class PersonManager extends AbstractManager<Person, PersonRepository> {

	@Autowired
	AccessTokenRepository accessTokenRepository;

	@Autowired
	public PersonManager(PersonRepository personRepository) {
		super(personRepository);
	}

	public Person findByAccount(Account account) {
		return getRepository().findByOwningAccountId(account.getId());
	}

	public Person findByToken(String tokenText) {
		AccessToken token = accessTokenRepository.findByTokenText(tokenText);
		return getRepository().findByOwningAccountId(token.getOwningAccountId());
	}
}
