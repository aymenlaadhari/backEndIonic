package de.fiducia.sharea.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.fiduciagad.sharea.server.App;
import de.fiduciagad.sharea.server.data.access.AccountManager;

@ActiveProfiles("testing")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
// @WebIntegrationTest(":9090")
public class AbstractShareATest {

	@Autowired
	private AccountManager accountManager;

	public AbstractShareATest() {
	}

	@Before
	public void setUp() {
		accountManager.createDeveloperAccount("Testus Juser", "none@example.com",
				"f0bf325a642c300614f31a72084c5ef11609a1ae0a0fba52", "testuser");
	}

	@After
	public void tearDown() {
		// TODO Delete data and stuff...
	}

}
