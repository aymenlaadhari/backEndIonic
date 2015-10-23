package de.fiduciagad.hackathon.superteam.server.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	private static final Log log = LogFactory
			.getLog(HelloWorldController.class);

	private static final String template = "Hello: %s";
	private final AtomicLong counter = new AtomicLong();

	@CrossOrigin
	@RequestMapping(value = "/hello-world", method = RequestMethod.GET)
	@ResponseBody
	public Greeting sayHello(
			@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
		Greeting greeting = new Greeting(counter.incrementAndGet(),
				String.format(template, name));
		return greeting;
	}

	@CrossOrigin
	@RequestMapping(value = "/data", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> data(@RequestBody Map<String, String> payload)
			throws Exception {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("A", "BCD");

		log.info("data (in): " + payload.toString());
		log.info("data (out): " + data.toString());

		return data;
	}

	@CrossOrigin
	@RequestMapping(value = "/watson-voice", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> watsonVoice(
			@RequestBody Map<String, String> payload) throws Exception {
		String string = payload.get("wavFile");
		String base64EncodedString = string.substring(string.indexOf(',') + 1);
		String format = payload.get("format");
		String rate = payload.get("rate");

		byte[] decode = Base64Utils.decode(base64EncodedString.getBytes());

		AudioFormat newFormat = new AudioFormat(16000.0F, 16, 1, true, false);
		AudioInputStream ais = AudioSystem
				.getAudioInputStream(new ByteArrayInputStream(decode));
		AudioInputStream lowResAIS = AudioSystem.getAudioInputStream(newFormat,
				ais);

		File tmpFile = File.createTempFile("hackathon", "wav");

		AudioSystem.write(lowResAIS, AudioFileFormat.Type.WAVE, tmpFile);

		String convert = WatsonProvider.convert(tmpFile, format + ";rate="
				+ rate);
		tmpFile.delete();

		HashMap<String, String> data = new HashMap<String, String>();
		data.put("text", convert);
		return data;
	}

	@CrossOrigin
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Person> list(@RequestBody Map<String, String> payload)
			throws Exception {
		List<Person> data = new ArrayList<Person>();
		data.add(generateAlex());
		data.add(generateAlex());
		data.add(generateAlex());
		data.add(generateAlex());
		data.add(generateAlex());
		data.add(generateAlex());
		return data;
	}

	private Person generateAlex() {
		Person person = new Person();
		person.setName("Alexander Widak");
		person.setAbteilung("ARIAK");
		person.setAktivitaetsindex(1);
		person.setMaximalgroesse(0);
		person.setLat("48.992850");
		person.setLng("8.448124");
		person.setAffinitaet(0);
		person.setStartzeit("2011-07-14 19:43:37 +0100");
		return person;
	}

}