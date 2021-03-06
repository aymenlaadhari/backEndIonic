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
		accountManager.createDeveloperAccount("Daniel Betsche", "daniel.betsche@fiduciagad.de", "af58533c4911aa9a0db1185d34be355af82821ec3b1a5ae4","Daniel");
		accountManager.createDeveloperAccount("Aymen Laadhari", "aymen.laadhari@fiduciagad.de", "d273a9c52d546c6d48b52d5d87c02ab989bc2d6579a8e229","Aymen");
		accountManager.createDeveloperAccount("Simon Egner", "simon.egner@fiduciagad.de", "d456a9c52d536c6d28b53d5d87c02ab989bc2d6579a8e229","Simon");
		accountManager.createDeveloperAccount("Jens Zimmermann", "jens.zimmermann@fiduciagad.de", "d678a9c52d546c6d48b52d5d87c02ab989bc2d6579a8e229","Jens");
		accountManager.createDeveloperAccount("Peter Fichtner", "peter.fichtner@fiduciagad.de", "d789a9c52d546c6d48b52d5d87c02ab989bc2d6579a8e229","Peter");
		accountManager.createDeveloperAccount("Testus Juser", "none@example.com", "f0bf325a642c300614f31a72084c5ef11609a1ae0a0fba52","testuser");
		//@formatter:on
	}

}
