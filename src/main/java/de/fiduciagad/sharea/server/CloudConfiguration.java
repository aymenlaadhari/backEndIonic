package de.fiduciagad.sharea.server;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudConfiguration extends AbstractCloudConfig {

	@Value("${CLOUDANT_DB_NAME}")
	private String dbName;

	@Autowired
	Cloud cloud;

	public CloudConfiguration() {
	}

	@Bean
	public CouchDbConnector couchDbConnector() {
		return couchDbInstance().createConnector(dbName, true);
	}

	@Bean
	public CouchDbInstance couchDbInstance() {
		// If more than one Cloudant DB is bound to the app this will throw an
		// error. This is intentional to prevent more than one Cloudant
		// database.
		return cloud.getSingletonServiceConnector(CouchDbInstance.class, null);
	}
}