package de.fiduciagad.hackathon.superteam.server;

import java.io.File;

import de.fiduciagad.hackathon.superteam.server.controllers.WatsonProvider;

public class TestMe {

	public static void main(String[] args) {
        System.setProperty("http.proxyHost", "http://proxy01-ka.noc.fiducia.de:8080");
        System.setProperty("http.proxyPort", "http://proxy01-ka.noc.fiducia.de:8080");
        System.setProperty("https.proxyHost", "http://proxy01-ka.noc.fiducia.de:8080");
        System.setProperty("https.proxyPort", "http://proxy01-ka.noc.fiducia.de:8080");
//		WatsonProvider.convert(new File("c:/temp/doctors_are_lying_chocolate_is_healthy.wav"), "audio/wav; rate=44100");
		
		WatsonProvider.convert(new File("c:/temp/doctors_are_lying_chocolate_is_healthy.wav"), "audio/l16; rate=44100");
	}

}
