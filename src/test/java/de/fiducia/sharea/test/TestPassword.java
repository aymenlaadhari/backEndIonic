package de.fiducia.sharea.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestPassword {
	PasswordEncoder pw = new BCryptPasswordEncoder();

	@Test
	public void TestM() {
		String rawPassword = "egon";
		String encoded = pw.encode(rawPassword);
		String encoded2 = pw.encode(rawPassword);
		assertTrue(pw.matches(rawPassword, encoded));
		assertTrue(pw.matches(rawPassword, encoded2));
	}
}
