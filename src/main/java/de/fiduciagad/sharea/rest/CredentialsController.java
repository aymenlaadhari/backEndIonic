package de.fiduciagad.sharea.rest;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.configuration.AppConfig;
import de.fiduciagad.sharea.server.configuration.DatabaseConfiguration;
import de.fiduciagad.sharea.server.dto.DBCredentials;

@RestController
public class CredentialsController {
	
	private DatabaseConfiguration dbconf;
	private DBCredentials credentials;
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/credentials")
	public DBCredentials getCredentials(@RequestParam(value="user", defaultValue="Test")String user, @RequestParam(value="password", defaultValue="Test")String pw){
		
		dbconf =new AppConfig().createDatabaseConfiguration();
		
		
		if(user.equals("Test")&&pw.equals("Test"))
			credentials = new DBCredentials(counter.getAndIncrement(), "TestURL");
		else
			credentials = new DBCredentials(counter.getAndIncrement(), dbconf.getUrl());
		
		
		return credentials;
	}
	
}
