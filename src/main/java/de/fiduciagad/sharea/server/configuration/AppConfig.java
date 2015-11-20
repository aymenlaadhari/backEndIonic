package de.fiduciagad.sharea.server.configuration;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Profile("cloud")
@Configuration
public class AppConfig {
	
	@Autowired
	Environment env;

	@Bean
	public DatabaseConfiguration createDatabaseConfiguration() {
		String VCAP_SERVICES = env.getProperty("VCAP_SERVICES");
		String CLOUDANT_CONFIG_KEY = env.getProperty("CLOUDANT_CONFIG_KEY");
		String CLOUDANT_DB_NAME = env.getProperty("CLOUDANT_DB_NAME");

		if (isNotEmpty(VCAP_SERVICES) && isNotEmpty(CLOUDANT_CONFIG_KEY) && isNotEmpty(CLOUDANT_DB_NAME)) {
			JsonObject obj = (JsonObject) new JsonParser().parse(VCAP_SERVICES);
			JsonElement dbEntry = null;

			if (obj.has(CLOUDANT_CONFIG_KEY)) {
				dbEntry = obj.get(CLOUDANT_CONFIG_KEY);
				if (dbEntry.isJsonArray()) {
					dbEntry = dbEntry.getAsJsonArray().get(0);
				}
				JsonObject credentialsObject = dbEntry.getAsJsonObject();
				JsonObject credentials = credentialsObject.get("credentials").getAsJsonObject();
				
				String username = credentials.get("username").getAsString();
				String password = credentials.get("password").getAsString();
				String url = credentials.get("url").getAsString();

				return new DatabaseConfiguration(username, password, CLOUDANT_DB_NAME,url);
			} else {
				throw new RuntimeException("Could not find " + CLOUDANT_CONFIG_KEY + " key in " + VCAP_SERVICES + ".");
			}
		} else {
			throw new RuntimeException(
					"Not all required env-vars set: VCAP_SERVICES, CLOUDANT_CONFIG_KEY, CLOUDANT_DB_NAME.");
		}
	}

}