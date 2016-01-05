package de.fiduciagad.sharea.server.rest;

import java.util.Collections;
import java.util.Map;

import org.ektorp.CouchDbInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.repository.AccessTokenRepository;
import de.fiduciagad.sharea.server.data.repository.AccountRepository;
import de.fiduciagad.sharea.server.data.repository.CategoryRepository;
import de.fiduciagad.sharea.server.data.repository.EventRepository;
import de.fiduciagad.sharea.server.data.repository.PersonRepository;
import de.fiduciagad.sharea.server.data.repository.ShareRepository;

/**
 * REST API which should enable developers to perform some basic database
 * maintanence tasks.
 *
 * The API is disabled when the app is running with a profile defined.
 *
 * @author xck1064
 *
 */
@RestController
@ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "dev")
public class DatabaseController {

	@Autowired
	private CouchDbInstance couchDbInstance;

	@Autowired
	private AccessTokenRepository accessTokenRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private ShareRepository shareRepository;

	@Value("${CLOUDANT_DB_NAME}")
	private String dbName;;

	/**
	 * Delete all Documents from the Database and regenerate design document and
	 * views.
	 *
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "/api/v1/data", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, Boolean> purge() {
		try {
			// TODO1 ensure user is admin
			couchDbInstance.deleteDatabase(dbName);
			couchDbInstance.createDatabase(dbName);

			accessTokenRepository.initStandardDesignDocument();
			accountRepository.initStandardDesignDocument();
			categoryRepository.initStandardDesignDocument();
			eventRepository.initStandardDesignDocument();
			personRepository.initStandardDesignDocument();
			shareRepository.initStandardDesignDocument();
		} catch (Exception e) {
			throw new IllegalStateException("Could not clean database.", e);
		}

		return Collections.singletonMap("success", Boolean.TRUE);
	}

}
