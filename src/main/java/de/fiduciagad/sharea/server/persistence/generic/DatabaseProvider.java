package de.fiduciagad.sharea.server.persistence.generic;

import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lightcouch.CouchDbException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
@Scope("singleton")
@PropertySource("classpath:de/fiduciagad/sharea/server/config/cloudant.properties")
public class DatabaseProvider {
	private static final Log log = LogFactory.getLog(DatabaseProvider.class);

	private CloudantClient cloudant = null;
	private Database db = null;

	private static final String ENCODING = "UTF-8";

	@Value("${cloudant.dbname}")
	private String databaseName = "todos";
	@Value("${cloudant.url}")
	private String url = null;
	@Value("${cloudant.user}")
	private String user = null;
	@Value("${cloudant.password}")
	private String password = null;

	@PostConstruct
	private void initClient() {
		if (cloudant == null) {
			synchronized (DatabaseProvider.class) {
				if (cloudant != null) {
					return;
				}
				cloudant = createClient();

			}
		}
		getDB();
	}

	private CloudantClient createClient() {
		/*
		 * Wenn wir auf der Bluemix Maschine sind, dann haben wir auch die VCAP
		 * Variablen gesetzt, ansonsten holt der sich aus den Properties die
		 * aktuelle Config der CloudantDB
		 */
		// VCAP_SERVICES is a system environment variable
		// Parse it to obtain the NoSQL DB connection info
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		String serviceName = null;

		if (VCAP_SERVICES != null) {
			// parse the VCAP JSON structure
			JsonObject obj = (JsonObject) new JsonParser().parse(VCAP_SERVICES);
			Entry<String, JsonElement> dbEntry = null;
			Set<Entry<String, JsonElement>> entries = obj.entrySet();
			// Look for the VCAP key that holds the cloudant no sql db
			// information
			for (Entry<String, JsonElement> eachEntry : entries) {
				if (eachEntry.getKey().equals("cloudantNoSQLDB")) {
					dbEntry = eachEntry;
					break;
				}
			}
			if (dbEntry == null) {
				throw new RuntimeException("Could not find cloudantNoSQLDB key in VCAP_SERVICES env variable");
			}

			obj = (JsonObject) ((JsonArray) dbEntry.getValue()).get(0);
			serviceName = (String) dbEntry.getKey();
			System.out.println("Service Name - " + serviceName);

			obj = (JsonObject) obj.get("credentials");

			user = obj.get("username").getAsString();
			password = obj.get("password").getAsString();
			url = obj.get("url").getAsString();

		}

		try {
			return new CloudantClient(url, user, password);

		} catch (CouchDbException e) {
			throw new RuntimeException("Unable to connect to repository", e);
		}
	}

	public Database getDB() {

		if (db == null) {
			try {
				db = cloudant.database(databaseName, false);
			} catch (Exception e) {
				throw new RuntimeException("DB Not found", e);
			}
		}
		return db;
	}

}
