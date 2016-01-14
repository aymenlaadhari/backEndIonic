package de.fiduciagad.sharea.server.data.repository.init;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "dev")
public class DemoDataInitialization {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Categories categories;

	@Autowired
	private Shares shares;

	@Autowired
	private Persons persons;

	@Autowired
	private DeveloperAccounts developerAccounts;

	public DemoDataInitialization() {
	}

	@PostConstruct
	public void init() {
		try {
			developerAccounts.init();
			List<String> personIds = persons.init();
			Map<String, String> categoryMapping = categories.init();
			log.info("init shares, personIds: ");
			personIds.forEach((s) -> log.info(s));
			log.info("categories mapping: ");
			categoryMapping.forEach((s,t) -> log.info("categorie: "+ s + " " + t));
			shares.init(personIds, categoryMapping);
		} catch (Throwable t) {
			log.error("init error:", t);
			throw new IllegalStateException(t);
		}
	}

}
