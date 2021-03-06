package de.fiducia.sharea.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.fiduciagad.sharea.server.data.repository.dto.AccessToken;

public class PasswordTest {
	PasswordEncoder pw = new BCryptPasswordEncoder();

	/**
	 * Not a real test. But I need access tokens.
	 *
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	@Test
	public void generateRandomAccessTokens() throws GeneralSecurityException, IOException {
		assertFalse(AccessToken.createRandom("No", "Clue").getTokenText()
				.equals(AccessToken.createRandom("No", "Clue").getTokenText()));
	}

	@Test
	public void TestM() {
		String rawPassword = "egon";
		String encoded = pw.encode(rawPassword);
		String encoded2 = pw.encode(rawPassword);
		assertTrue(pw.matches(rawPassword, encoded));
		assertTrue(pw.matches(rawPassword, encoded2));
	}
}
