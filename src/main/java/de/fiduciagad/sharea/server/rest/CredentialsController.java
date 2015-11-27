package de.fiduciagad.sharea.server.rest;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.configuration.DatabaseConfiguration;
import de.fiduciagad.sharea.server.dto.DBCredentials;

@RestController
public class CredentialsController {

	@Autowired
	private DatabaseConfiguration dbconf;

	private DBCredentials credentials;

	private final AtomicLong counter = new AtomicLong();

	@CrossOrigin
	@RequestMapping(value = "/credentials", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public DBCredentials getCredentials(@RequestParam(value = "user", required = false) String user,
			@RequestParam(value = "password", required = false) String pw) {

		System.out.println("entered credentials Service");

		credentials = new DBCredentials(counter.getAndIncrement(), dbconf.getUrl());
		return credentials;
	}

}
