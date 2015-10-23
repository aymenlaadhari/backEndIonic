package de.fiduciagad.hackathon.superteam.server.controllers;

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

		if (VCAP_SERVICES != null) {
			JsonObject obj = (JsonObject) new JsonParser().parse(VCAP_SERVICES);
			Entry<String, JsonElement> dbEntry = null;
			Set<Entry<String, JsonElement>> entries = obj.entrySet();
			for (Entry<String, JsonElement> eachEntry : entries) {
				if (eachEntry.getKey().equals("speech_to_text")) {
					dbEntry = eachEntry;
					break;
				}
			}
			if (dbEntry == null) {
				throw new RuntimeException(
						"Could not find speech_to_text key in VCAP_SERVICES env variable");
			}

			obj = (JsonObject) ((JsonArray) dbEntry.getValue()).get(0);

			obj = (JsonObject) obj.get("credentials");

			String user = obj.get("username").getAsString();
			String password = obj.get("password").getAsString();

			service.setUsernameAndPassword(user, password);
		} else {
			throw new RuntimeException("VCAP_SERVICES not found");
		}

		return service;
	}

}
