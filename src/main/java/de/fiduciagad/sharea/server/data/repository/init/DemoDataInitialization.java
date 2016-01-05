package de.fiduciagad.sharea.server.data.repository.init;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "dev")
public class DemoDataInitialization {

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
		developerAccounts.init();
		List<String> personIds = persons.init();
		Map<String, String> categoryMapping = categories.init();
		shares.init(personIds, categoryMapping);
	}

}
