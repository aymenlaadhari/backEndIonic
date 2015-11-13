package de.fiduciagad.sharea.server.other;

import java.io.File;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechAlternative;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;

public class WatsonProvider {

	private static SpeechToText service = null;

	public static String convert(File audio, String contentType) {
		if (service == null) {
			service = initService();
		}
		StringBuffer sb = new StringBuffer();
		SpeechResults transcript = service.recognize(audio, contentType);
		for (Transcript ts : transcript.getResults()) {
			for (SpeechAlternative alternative : ts.getAlternatives()) {
				sb.append(alternative.getTranscript());
				sb.append(" ");
			}
		}
		return sb.toString().trim();
	}

	private static SpeechToText initService() {
		SpeechToText service = new SpeechToText();

		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		String CLOUDANT_CONFIG_KEY = System.getenv("CLOUDANT_CONFIG_KEY");

		if (VCAP_SERVICES != null) {
			JsonObject obj = (JsonObject) new JsonParser().parse(VCAP_SERVICES);
			Entry<String, JsonElement> dbEntry = null;
			Set<Entry<String, JsonElement>> entries = obj.entrySet();
			for (Entry<String, JsonElement> eachEntry : entries) {
				if (eachEntry.getKey().equals(CLOUDANT_CONFIG_KEY)) {
					dbEntry = eachEntry;
					break;
				}
			}
			if (dbEntry == null) {
				throw new RuntimeException(
						"Could not find " + CLOUDANT_CONFIG_KEY + " key in VCAP_SERVICES.");
			}

			obj = (JsonObject) ((JsonArray) dbEntry.getValue()).get(0);

			obj = (JsonObject) obj.get("credentials");

			String user = obj.get("username").getAsString();
			String password = obj.get("password").getAsString();

			service.setUsernameAndPassword(user, password);
		} else {
			throw new RuntimeException("VCAP_SERVICES or CLOUDANT_CONFIG_KEY not found in environment variables.");
		}

		return service;
	}

}
