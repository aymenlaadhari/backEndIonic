package de.fiduciagad.sharea.rest;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.configuration.DatabaseConfiguration;

@RestController
public class CredentialsController {

	@Autowired
	private DatabaseConfiguration dbconf;

	@CrossOrigin
	@RequestMapping(value = "/credentials", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> getCredentials(@RequestParam(value = "user", required = false) String user,
			@RequestParam(value = "password", required = false) String pw) {
		return Collections.singletonMap("credentials", dbconf.getUrl());
	}

}
