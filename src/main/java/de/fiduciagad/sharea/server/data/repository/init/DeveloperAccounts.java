package de.fiduciagad.sharea.server.data.repository.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.access.AccountManager;

@Component
public class DeveloperAccounts {

	@Autowired
	private AccountManager accountManager;

	public DeveloperAccounts() {
	}

	public void init() {
		//@formatter:off
		accountManager.createDeveloperAccount("Adrian Rochau", "adrian.rochau@fiduciagad.de", "5a70af57d61b0145ea47d352973c657a38630aa79cb31383","Adrian");
		accountManager.createDeveloperAccount("Alexander Widak", "alexander.widak@fiduciagad.de", "ab5e63f94af99a3ebcb1bd5189083366e5c3de3cb0cd4a8c","Alex");
		accountManager.createDeveloperAccount("Matthias Weber", "matthias.weber@fiduciagad.de", "310b7ea07cca378da89d1dcd85b6710934f554a2206a4551","Matze");
		accountManager.createDeveloperAccount("Maximilian Geißel", "maximilian.geissel@fiduciagad.de", "a5078697e93d07ef0f95f482ee13876256f557ff7af57697","Max");
		accountManager.createDeveloperAccount("Rudolf Mottinger", "rudolf.mottinger@fiduciagad.de", "7a9b6d34d4e6830015a7dd1f302fe29fec9c06a8e6ec5b6e","Rudi");
		accountManager.createDeveloperAccount("Daniel Betsche", "daniel.betsche@fiduciagad.de", "d273a9c52d546c6d48b52d5d87c02ab989bc2d6579a8e229","Daniel");
		accountManager.createDeveloperAccount("Aymen Laadhari", "aymen.laadhari@fiduciagad.de", "d123a9c52d546c6d48c52d5d87d76ab989bc2d6579a8e229","Aymen");
		//@formatter:on
	}

}
