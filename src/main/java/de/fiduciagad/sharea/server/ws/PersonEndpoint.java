package de.fiduciagad.sharea.server.ws;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.dto.Person;
import de.fiduciagad.sharea.server.persistence.dao.PersonDaoIf;

@RestController
public class PersonEndpoint {

	private static final Log log = LogFactory.getLog(PersonEndpoint.class);

	private static final String template = "Hello: %s";
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private PersonDaoIf personDao;
	
	
	
	@CrossOrigin
	@RequestMapping(value = "/personen", method = RequestMethod.GET)
	@ResponseBody
	public Person getPerson(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) throws IOException {
		
		Person person = personDao.readById("899cb7bfda45b838226232aa62d48288");
		
		return person;
	}
	
	
//	 @CrossOrigin
//	 @RequestMapping(value = "/data", method = RequestMethod.POST, produces =
//	 "application/json", consumes = "application/json")
//	 @ResponseBody
//	 public Map<String, String> data(@RequestBody Map<String, String> payload)
//	 throws Exception {
//	 HashMap<String, String> data = new HashMap<String, String>();
//	 data.put("A", "BCD");
//	
//	 log.info("data (in): " + payload.toString());
//	 log.info("data (out): " + data.toString());
//	
//	 return data;
//	 }
	//
	// @CrossOrigin
	// @RequestMapping(value = "/watson-voice", method = RequestMethod.POST,
	// produces = "application/json", consumes = "application/json")
	// @ResponseBody
	// public Map<String, String> watsonVoice(
	// @RequestBody Map<String, String> payload) throws Exception {
	// String string = payload.get("wavFile");
	// String base64EncodedString = string.substring(string.indexOf(',') + 1);
	// String format = payload.get("format");
	// String rate = payload.get("rate");
	//
	// byte[] decode = Base64Utils.decode(base64EncodedString.getBytes());
	//
	// AudioFormat newFormat = new AudioFormat(16000.0F, 16, 1, true, false);
	// AudioInputStream ais = AudioSystem
	// .getAudioInputStream(new ByteArrayInputStream(decode));
	// AudioInputStream lowResAIS = AudioSystem.getAudioInputStream(newFormat,
	// ais);
	//
	// File tmpFile = File.createTempFile("hackathon", "wav");
	//
	// AudioSystem.write(lowResAIS, AudioFileFormat.Type.WAVE, tmpFile);
	//
	// String convert = WatsonProvider.convert(tmpFile, format + ";rate="
	// + rate);
	// tmpFile.delete();
	//
	// HashMap<String, String> data = new HashMap<String, String>();
	// data.put("text", convert);
	// return data;
	// }
	//
	// @CrossOrigin
	// @RequestMapping(value = "/list", method = RequestMethod.GET, produces =
	// "application/json", consumes = "application/json")
	// @ResponseBody
	// public List<Person> list(@RequestBody Map<String, String> payload)
	// throws Exception {
	// List<Person> data = new ArrayList<Person>();
	// data.add(generateAlex());
	// data.add(generateAlex());
	// data.add(generateAlex());
	// data.add(generateAlex());
	// data.add(generateAlex());
	// data.add(generateAlex());
	// return data;
	// }
	//
	// private Person generateAlex() {
	// Person person = new Person();
	// person.setName("Alexander Widak");
	// person.setAbteilung("ARIAK");
	// person.setAktivitaetsindex(1);
	// person.setMaximalgroesse(0);
	// person.setLat("48.992850");
	// person.setLng("8.448124");
	// person.setAffinitaet(0);
	// person.setStartzeit("2011-07-14 19:43:37 +0100");
	// return person;
	// }

}