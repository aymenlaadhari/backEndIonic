package de.fiduciagad.sharea.server.persistence.generic;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import de.fiduciagad.sharea.server.configuration.DatabaseConfiguration;

@Service
public class DatabaseProvider {

	@Autowired
	DatabaseConfiguration dbConfiguration;

	private CloudantClient client = null;

	private Database database = null;

	@PostConstruct
	private void initClient() {
		if (client == null) {
			synchronized (DatabaseProvider.class) {
				if (client != null) {
					return;
				}
				client = ClientBuilder//
						.account(dbConfiguration.getUsername())//
						.username(dbConfiguration.getUsername())//
						.password(dbConfiguration.getPassword())//
						.build();
				database = client.database(dbConfiguration.getDatabase(), true);
			}
		}
	}

	public Database getDatabase() {
		return database;
	}

}